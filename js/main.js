// v8 main.js - POO completa + roles + detalles con persistencia
const classFiles = [
  'classes/Usuario.js','classes/Propuesta.js','classes/Emprendedor.js','classes/Estudiante.js','classes/Inversionista.js','classes/Voluntario.js','classes/Donante.js'
];

function loadSequential(list, base, cb){
  let i=0; (function next(){ if(i>=list.length) return cb(); const s=document.createElement('script'); s.src=base+'/'+list[i]; s.onload=()=>{i++;next()}; s.onerror=()=>{console.warn('No se pudo cargar',list[i]); i++; next();}; document.head.appendChild(s); })();
}
loadSequential(classFiles,'./js', initApp);

function initApp(){
  // ---- DB helpers ----
  const LS_USERS='ks_users', LS_PROJ='ks_projects', LS_SESSION='ks_session';
  const DB = { load:(k,f)=>{try{const r=localStorage.getItem(k); return r?JSON.parse(r):f;}catch(e){return f}}, save:(k,v)=>localStorage.setItem(k,JSON.stringify(v)) };
  const uid=()=>Date.now()+Math.floor(Math.random()*999);
  const toB64=(file,cb)=>{const r=new FileReader(); r.onload=()=>cb(r.result); r.readAsDataURL(file);};

  // revive
  function reviveUsers(arr){
    return arr.map(u=>{
      const t=(u.tipoUsuario||u.role||'estudiante').toLowerCase();
      if(t==='emprendedor') return new Emprendedor(u.id,u.nombre,u.correo,u.contrasena||u.password,u.tipoUsuario||u.role);
      if(t==='inversionista') return new Inversionista(u.id,u.nombre,u.correo,u.contrasena||u.password,u.tipoUsuario||u.role);
      if(t==='voluntario') return new Voluntario(u.id,u.nombre,u.correo,u.contrasena||u.password,u.tipoUsuario||u.role);
      if(t==='donante') return new Donante(u.id,u.nombre,u.correo,u.contrasena||u.password,u.tipoUsuario||u.role, u.montoDisponible||0);
      return new Estudiante(u.id,u.nombre,u.correo,u.contrasena||u.password,u.tipoUsuario||u.role);
    });
  }
  function reviveProjects(arr, users){
    return arr.map(p=>{
      const creador = users.find(u=>u.id===p.creadorId) || null;
      const pr = new Propuesta(p.id,p.titulo||p.nombre,p.descripcion,p.montoSolicitado||p.goal||p.montoMeta||0,creador);
      pr.collected = p.collected || p.montoRecaudado || 0;
      pr.img = p.img || p.imagen || '';
      pr.voluntarios = p.voluntarios || p.ayudas || [];
      pr.comentarios = p.comentarios || [];
      return pr;
    });
  }

  // seed
  if(!localStorage.getItem(LS_USERS)){
    const u1=new Emprendedor(1,'Emprendedor Demo','emprendedor@demo.com','1234','Emprendedor');
    const u2=new Inversionista(2,'Inversionista Demo','inversionista@demo.com','1234','Inversionista');
    const u3=new Voluntario(3,'Voluntario Demo','voluntario@demo.com','1234','Voluntario');
    DB.save(LS_USERS,[u1,u2,u3]);
  }
  if(!localStorage.getItem(LS_PROJ)){
    const users = reviveUsers(DB.load(LS_USERS,[]));
    const p1=new Propuesta(1,'Huerto Solar Comunitario','Huerto urbano con paneles solares',5000,users[0]); p1.collected=1200; p1.img='./img/huerto.jpg';
    const p2=new Propuesta(2,'App Educativa Rural','Plataforma móvil para educación rural',8000,users[0]); p2.collected=3400; p2.img='./img/app.jpg';
    const p3=new Propuesta(3,'Capacitación Técnica para Jóvenes','Cursos técnicos y prácticos',3000,users[0]); p3.collected=600; p3.img='./img/capacitacion.jpg';
    p1.voluntarios=[]; p1.comentarios=[]; p2.voluntarios=[]; p2.comentarios=[]; p3.voluntarios=[]; p3.comentarios=[];
    DB.save(LS_PROJ,[p1,p2,p3]);
  }

  let users = reviveUsers(DB.load(LS_USERS,[]));
  let projects = reviveProjects(DB.load(LS_PROJ,[]), users);
  let session = DB.load(LS_SESSION,null);

  // DOM
  const authArea = document.getElementById('auth-area');
  const projectsRoot = document.getElementById('projects');
  const modalRoot = document.getElementById('modal-root');
  const searchInput = document.getElementById('search');
  const btnAdd = document.getElementById('btn-add');

  // auth UI
  function renderAuth(){
    if(!authArea) return;
    authArea.innerHTML='';
    if(session){
      const box=document.createElement('div'); box.style.display='flex'; box.style.gap='8px'; box.style.alignItems='center';
      const span=document.createElement('span'); span.textContent = session.nombre + ' (' + session.tipoUsuario + ')'; span.className='small';
      const logout=document.createElement('button'); logout.textContent='Salir'; logout.className='btn btn-ghost'; logout.onclick=()=>{ localStorage.removeItem(LS_SESSION); session=null; renderAuth(); renderProjects(); };
      box.appendChild(span); box.appendChild(logout); authArea.appendChild(box);
    } else {
      const loginBtn=document.createElement('button'); loginBtn.textContent='Iniciar sesión'; loginBtn.className='btn btn-ghost'; loginBtn.onclick=openLoginModal;
      const regBtn=document.createElement('button'); regBtn.textContent='Registrarse'; regBtn.className='btn btn-primary'; regBtn.onclick=openRegisterModal;
      authArea.appendChild(loginBtn); authArea.appendChild(regBtn);
    }
  }

  // helpers
  function projectCardHTML(p){
    const goal = Number(p.montoSolicitado||p.goal||0)||0;
    const raised = Number(p.collected||0)||0;
    const pct = goal>0 ? Math.min(Math.round((raised/goal)*100),100) : 0;
    const imgHTML = (p.img && String(p.img).trim()!=='')
      ? `<div class="project-img-wrapper"><img class="project-img" src="${p.img}" alt="${p.titulo||'Proyecto'}"/></div>`
      : `<div class="project-img-wrapper"><div class="no-image">Sin imagen</div></div>`;
    return `<div class="card project-card">
      ${imgHTML}
      <div class="card-content">
        <h3>${p.titulo||'Proyecto'}</h3>
        <div class="small">${p.descripcion||''}</div>
        <div class="progress-bar"><div class="progress-bar-fill" style="width:${pct}%"></div></div>
        <div class="progress-info"><span>$${raised} recaudado</span><span>${pct}% de $${goal}</span></div>
        <div style="margin-top:10px">
          <button class="btn btn-primary" data-action="detalles" data-id="${p.id}">Ver detalles</button>
          <button class="btn btn-ghost" data-action="donar" data-id="${p.id}">Donar</button>
        </div>
      </div>
    </div>`;
  }

  function renderProjects(filter=''){
    if(!projectsRoot) return;
    const isList = document.body && document.body.dataset && document.body.dataset.page==='list';
    let list = isList ? projects.slice() : projects.slice(0,3);
    const filtered = list.filter(p=>{
      if(!filter) return true;
      const q = filter.toLowerCase();
      return (p.titulo && p.titulo.toLowerCase().includes(q)) || (p.descripcion && p.descripcion.toLowerCase().includes(q));
    });
    projectsRoot.innerHTML = filtered.map(projectCardHTML).join('');
    // bind actions
    projectsRoot.querySelectorAll('button[data-action="donar"]').forEach(btn=>{
      const id = Number(btn.getAttribute('data-id'));
      const pr = projects.find(x=>x.id===id);
      btn.onclick = ()=> openDonateModal(pr);
    });
    projectsRoot.querySelectorAll('button[data-action="detalles"]').forEach(btn=>{
      const id = Number(btn.getAttribute('data-id'));
      const pr = projects.find(x=>x.id===id);
      btn.onclick = ()=> openDetailsModal(pr);
    });
  }

  if(searchInput) searchInput.addEventListener('input', e=> renderProjects(e.target.value));
  if(btnAdd) btnAdd.addEventListener('click', ()=>{ if(!session) return alert('Debes iniciar sesión para crear proyectos'); openAddModal(); });

  // modal infra
  function openModal(html){
    if(!modalRoot) return {close:()=>{}};
    modalRoot.innerHTML=''; modalRoot.classList.add('show');
    const wrap=document.createElement('div'); wrap.className='modal'; wrap.innerHTML=html;
    modalRoot.appendChild(wrap);
    return { panel:wrap, close:()=>{ modalRoot.classList.remove('show'); modalRoot.innerHTML=''; } };
  }
  window.__closeModal = function(){ if(modalRoot){ modalRoot.classList.remove('show'); modalRoot.innerHTML=''; } };

  // login/register with roles
  function openLoginModal(){
    const html = `<div class="modal"><button class="close-x" onclick="__closeModal()">×</button>
      <h3>Iniciar sesión</h3>
      <div class="form-row"><input id="li-email" placeholder="Correo"></div>
      <div class="form-row"><input id="li-pass" type="password" placeholder="Contraseña"></div>
      <div style="margin-top:12px"><button id="mi-login" class="btn btn-primary">Entrar</button></div></div>`;
    const m=openModal(html);
    m.panel.querySelector('#mi-login').onclick=()=>{
      const email=m.panel.querySelector('#li-email').value.trim();
      const pass=m.panel.querySelector('#li-pass').value.trim();
      const raw=DB.load(LS_USERS,[]);
      const u = raw.find(x=>x.correo===email && (x.contrasena===pass || x.password===pass));
      if(!u) return alert('Credenciales inválidas');
      session = reviveUsers([u])[0]; DB.save(LS_SESSION,session); renderAuth(); renderProjects(); m.close();
    };
  }
  function openRegisterModal(){
    const html = `<div class="modal"><button class="close-x" onclick="__closeModal()">×</button>
      <h3>Registrarse</h3>
      <div class="form-row"><input id="re-name" placeholder="Nombre completo"></div>
      <div class="form-row"><input id="re-email" placeholder="Correo"></div>
      <div class="form-row"><input id="re-pass" type="password" placeholder="Contraseña"></div>
      <div class="form-row"><select id="re-role">
        <option value="estudiante">Estudiante / Emprendedor</option>
        <option value="emprendedor">Emprendedor</option>
        <option value="inversionista">Inversionista</option>
        <option value="voluntario">Voluntario</option>
        <option value="donante">Donante</option>
      </select></div>
      <div style="margin-top:12px"><button id="mi-register" class="btn btn-primary">Crear cuenta</button></div></div>`;
    const m=openModal(html);
    m.panel.querySelector('#mi-register').onclick=()=>{
      const name=m.panel.querySelector('#re-name').value.trim();
      const email=m.panel.querySelector('#re-email').value.trim();
      const pass=m.panel.querySelector('#re-pass').value.trim();
      const role=m.panel.querySelector('#re-role').value;
      if(!email||!pass) return alert('Correo y contraseña requeridos');
      const raw=DB.load(LS_USERS,[]);
      if(raw.find(x=>x.correo===email)) return alert('Email ya registrado');
      const id=uid(); let newUser;
      switch(role){
        case 'emprendedor': newUser = new Emprendedor(id,name,email,pass,role); break;
        case 'inversionista': newUser = new Inversionista(id,name,email,pass,role); break;
        case 'voluntario': newUser = new Voluntario(id,name,email,pass,role); break;
        case 'donante': newUser = new Donante(id,name,email,pass,role,0); break;
        case 'estudiante':
        default: newUser = new Emprendedor(id,name,email,pass,role); break;
      }
      raw.push(newUser); DB.save(LS_USERS,raw); session=newUser; DB.save(LS_SESSION,session); renderAuth(); renderProjects(); m.close();
    };
  }

  // add project
  function openAddModal(){
    const html = `<div class="modal"><button class="close-x" onclick="__closeModal()">×</button>
      <h3>Nuevo proyecto</h3>
      <div class="form-row"><input id="p-title" placeholder="Título"></div>
      <div class="form-row"><textarea id="p-desc" placeholder="Descripción"></textarea></div>
      <div class="form-row"><input id="p-goal" type="number" placeholder="Meta USD"></div>
      <div class="form-row"><input id="p-img" type="file" accept="image/*"></div>
      <div class="form-row"><img id="p-preview" style="max-width:100%;border-radius:8px;display:none"/></div>
      <div style="margin-top:12px"><button id="p-create" class="btn btn-primary">Crear proyecto</button></div></div>`;
    const m=openModal(html);
    const inp=m.panel.querySelector('#p-img'); const prev=m.panel.querySelector('#p-preview');
    inp.onchange=()=>{ if(inp.files[0]){ toB64(inp.files[0], (b64)=>{ prev.src=b64; prev.style.display='block'; }); } };
    m.panel.querySelector('#p-create').onclick=()=>{
      const title=m.panel.querySelector('#p-title').value.trim();
      const desc=m.panel.querySelector('#p-desc').value.trim();
      const goal=Number(m.panel.querySelector('#p-goal').value)||0;
      if(!title||!desc) return alert('Título y descripción requeridos');
      const raw=DB.load(LS_PROJ,[]);
      const id=uid(); const p=new Propuesta(id,title,desc,goal,session); p.collected=0;
      if(prev.src && prev.style.display!=='none') p.img=prev.src;
      p.voluntarios=[]; p.comentarios=[];
      raw.unshift(p); DB.save(LS_PROJ,raw); projects = reviveProjects(DB.load(LS_PROJ,[]), users); __closeModal(); renderProjects();
    };
  }

  // donate
  function openDonateModal(project){
    if(!session) return alert('Debes iniciar sesión para donar');
    const html = `<div class="modal"><button class="close-x" onclick="__closeModal()">×</button>
      <h3>Donar a ${project.titulo}</h3>
      <div class="form-row"><select id="d-type"><option value="money">Donación económica</option><option value="volunteer">Voluntariado</option></select></div>
      <div class="form-row"><input id="d-amount" type="number" placeholder="Monto USD (si es económico)"></div>
      <div class="form-row"><textarea id="d-text" placeholder="Describe tu ayuda (si voluntariado: mano de obra, capacitación, materiales, etc.)"></textarea></div>
      <div style="margin-top:12px"><button id="d-send" class="btn btn-primary">Confirmar</button></div></div>`;
    const m=openModal(html);
    m.panel.querySelector('#d-send').onclick=()=>{
      const type=m.panel.querySelector('#d-type').value;
      const amount=Number(m.panel.querySelector('#d-amount').value)||0;
      const text=m.panel.querySelector('#d-text').value.trim();
      const raw=DB.load(LS_PROJ,[]); const idx=raw.findIndex(r=>r.id===project.id); if(idx<0) return alert('Proyecto no encontrado');
      if(type==='money'){ if(amount<=0) return alert('Monto inválido'); raw[idx].collected=(raw[idx].collected||0)+amount; }
      else { raw[idx].voluntarios=raw[idx].voluntarios||[]; raw[idx].voluntarios.push({user:session.nombre, text:text||'Ayuda ofrecida', date:new Date().toISOString()}); }
      DB.save(LS_PROJ,raw); projects = reviveProjects(DB.load(LS_PROJ,[]), users); __closeModal(); renderProjects(); alert('¡Gracias por tu aporte!');
    };
  }

  // details modal (comentarios + ayudas juntos)
  function openDetailsModal(project){
    const goal = Number(project.montoSolicitado||project.goal||0)||0;
    const raised = Number(project.collected||0)||0;
    const pct = goal>0 ? Math.min(Math.round((raised/goal)*100),100) : 0;
    const imgHTML = (project.img && String(project.img).trim()!=='')
      ? `<div class="project-img-wrapper"><img class="project-img" src="${project.img}" alt="${project.titulo||'Proyecto'}"/></div>`
      : `<div class="project-img-wrapper"><div class="no-image">Sin imagen</div></div>`;
    const html = `<div class="modal"><button class="close-x" onclick="__closeModal()">×</button>
      <h3>${project.titulo}</h3>
      ${imgHTML}
      <div class="small" style="margin-top:8px">${project.descripcion||''}</div>
      <div class="progress-bar" style="margin-top:10px"><div class="progress-bar-fill" style="width:${pct}%"></div></div>
      <div class="progress-info"><span>$${raised} recaudado</span><span>${pct}% de $${goal}</span></div>
      <div style="margin-top:12px">
        <h4>Actividad</h4>
        <div id="activity" class="small" style="max-height:200px; overflow:auto; border:1px solid rgba(255,255,255,.08); padding:8px; border-radius:8px;"></div>
      </div>
      <div style="display:flex; gap:8px; flex-wrap:wrap; margin-top:12px">
        <button class="btn btn-primary" id="do-donate">Donar</button>
        ${session && (session.tipoUsuario||'').toLowerCase()==='inversionista' ? '<button class="btn btn-ghost" id="do-comment">Comentar</button>' : ''}
        ${session && (session.tipoUsuario||'').toLowerCase()==='voluntario' ? '<button class="btn btn-ghost" id="do-help">Ofrecer ayuda</button>' : ''}
      </div>
    </div>`;
    const m=openModal(html);
    const act = m.panel.querySelector('#activity');
    function renderActivity(){
      const raw=DB.load(LS_PROJ,[]);
      const pi = raw.find(r=>r.id===project.id);
      const comentarios = (pi && pi.comentarios) || [];
      const ayudas = (pi && (pi.voluntarios || pi.ayudas)) || [];
      const items = [];
      ayudas.forEach(a=> items.push({type:'ayuda', text:a.text||a, user:a.user||'Voluntario', date:a.date||new Date().toISOString()}));
      comentarios.forEach(c=> items.push({type:'comentario', text:c.text||c, user:c.user||'Inversionista', date:c.date||new Date().toISOString()}));
      items.sort((a,b)=> new Date(b.date)-new Date(a.date));
      if(items.length===0){ act.innerHTML = '<div class="small" style="opacity:.8">Aún no hay actividad.</div>'; return; }
      act.innerHTML = items.map(it=> `<div style="padding:6px 4px; border-bottom:1px solid rgba(255,255,255,.06)">
        <div style="font-weight:600">${it.user} • <span style="opacity:.8">${it.type}</span></div>
        <div>${it.text}</div>
        <div style="font-size:11px; opacity:.7">${new Date(it.date).toLocaleString()}</div>
      </div>`).join('');
    }
    renderActivity();

    const donateBtn = m.panel.querySelector('#do-donate');
    donateBtn.onclick = ()=>{ __closeModal(); openDonateModal(project); };

    const commentBtn = m.panel.querySelector('#do-comment');
    if(commentBtn){
      commentBtn.onclick = ()=>{
        const box = prompt('Escribe tu comentario:');
        if(!box) return;
        const raw=DB.load(LS_PROJ,[]);
        const idx=raw.findIndex(r=>r.id===project.id); if(idx<0) return;
        raw[idx].comentarios = raw[idx].comentarios||[]; raw[idx].comentarios.push({user:session.nombre, text:box, date:new Date().toISOString()});
        DB.save(LS_PROJ,raw); projects = reviveProjects(DB.load(LS_PROJ,[]), users); renderActivity();
      };
    }

    const helpBtn = m.panel.querySelector('#do-help');
    if(helpBtn){
      helpBtn.onclick = ()=>{
        const box = prompt('Describe tu ayuda (mano de obra, materiales, capacitación, mentoría, etc.):');
        if(!box) return;
        const raw=DB.load(LS_PROJ,[]);
        const idx=raw.findIndex(r=>r.id===project.id); if(idx<0) return;
        raw[idx].voluntarios = raw[idx].voluntarios||[]; raw[idx].voluntarios.push({user:session.nombre, text:box, date:new Date().toISOString()});
        DB.save(LS_PROJ,raw); projects = reviveProjects(DB.load(LS_PROJ,[]), users); renderActivity();
      };
    }
  }

  // initial render
  renderAuth(); renderProjects();

  // expose for list page and external buttons
  window.__AP = { users, projects, session }; dispatchEvent(new Event('ap-ready'));
  window.openDonateModal = openDonateModal;
  window.openDetailsModal = openDetailsModal;
}
