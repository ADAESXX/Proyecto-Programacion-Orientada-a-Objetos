// Vista.js — UI/DOM
class Vista {
  renderPortada(projects, { onDonate, onDetails } = {}){
    const root = document.querySelector('#projects-root') || document.querySelector('#home-projects') || document.querySelector('#cards-root') || document.querySelector('#app-root');
    if(!root) return;
    root.innerHTML = projects.map(p => `
      <div class="card project-card">
        ${this._img(p)}
        <h3>${this._esc(p.titulo)}</h3>
        <p>${this._esc(p.descripcion || '')}</p>
        <div class="meta">Q${Number(p.collected||0)} de Q${Number(p.goal||0)}</div>
        <div class="bar"><span style="width:${this._pct(p)}%"></span></div>
        <div class="actions">
          <button class="btn btn-ghost" data-act="donar" data-id="${this._esc(p.id)}">Donar</button>
          <button class="btn btn-ghost" data-act="detalles" data-id="${this._esc(p.id)}">Ver detalles</button>
        </div>
      </div>`).join('');
    root.querySelectorAll('button[data-act="donar"]').forEach(b=> b.onclick = () => onDonate && onDonate(b.dataset.id));
    root.querySelectorAll('button[data-act="detalles"]').forEach(b=> b.onclick = () => onDetails && onDetails(b.dataset.id));
  }
  renderLista(projects, { onDonate, onDetails } = {}){
    const root = document.querySelector('#projects-list-root') || document.querySelector('#projects-root') || document.querySelector('#lista-root') || document.querySelector('#app-root');
    if(!root) return;
    root.innerHTML = projects.map(p => `
      <div class="row project-row">
        <div class="left">
          ${this._thumb(p)}
          <div>
            <div class="title">${this._esc(p.titulo)}</div>
            <div class="sub">Q${Number(p.collected||0)} de Q${Number(p.goal||0)}</div>
            <div class="bar small"><span style="width:${this._pct(p)}%"></span></div>
          </div>
        </div>
        <div class="right">
          <button class="btn btn-ghost" data-act="donar" data-id="${this._esc(p.id)}">Donar</button>
          <button class="btn btn-ghost" data-act="detalles" data-id="${this._esc(p.id)}">Ver detalles</button>
        </div>
      </div>`).join('');
    root.querySelectorAll('button[data-act="donar"]').forEach(b=> b.onclick = () => onDonate && onDonate(b.dataset.id));
    root.querySelectorAll('button[data-act="detalles"]').forEach(b=> b.onclick = () => onDetails && onDetails(b.dataset.id));
  }
  bindGlobalButtons({ onOpenNotifications, onOpenMessages } = {}){
    const bell = document.getElementById('notif-fab');
    const msg  = document.getElementById('msg-fab');
    if(bell){ bell.onclick = (e)=>{ e.preventDefault(); onOpenNotifications && onOpenNotifications(); }; }
    if(msg){  msg.onclick  = (e)=>{ e.preventDefault(); onOpenMessages && onOpenMessages(); }; }
  }
  showNotifications(list, { onMarkAllRead } = {}){
    const html = `<h3>Notificaciones</h3>
      <div class="notif-list">
        ${list.length ? list.map(it => `
          <div class="notif-item">
            <div class="tit">${this._esc(it.projectTitle || 'Proyecto')} • <span>${this._esc(it.type)}</span></div>
            ${it.type==='donacion' ? `<div class="small">Monto: Q${Number(it.amount||0)} • Tipo: ${this._esc(it.donationType || '-')}</div>` : ''}
            ${it.message ? `<div class="small">Mensaje: ${this._esc(it.message)}</div>` : ''}
            ${it.commentText ? `<div class="small">Comentario: ${this._esc(it.commentText)}</div>` : ''}
            ${it.helpText ? `<div class="small">Ayuda ofrecida: ${this._esc(it.helpText)}</div>` : ''}
            <div class="small">de ${this._esc(it.fromUserName || 'Usuario')} • ${new Date(it.createdAt || Date.now()).toLocaleString()}</div>
          </div>`).join('') : '<div class="small">No tienes notificaciones.</div>'}
      </div>
      <div class="modal-actions">
        <button class="btn btn-ghost" id="notif-mark">Marcar como leídas</button>
        <button class="btn btn-primary" onclick="__closeModal()">Cerrar</button>
      </div>`;
    const m = this._openModal(html);
    const mark = m.panel.querySelector('#notif-mark');
    if(mark){ mark.onclick = ()=>{ onMarkAllRead && onMarkAllRead(); this._closeModal(); }; }
  }
  _img(p){ const url = p.img && String(p.img).trim(); return url ? `<img src="${this._esc(url)}" class="cover"/>` : `<div class="cover placeholder"></div>`; }
  _thumb(p){ const url = p.img && String(p.img).trim(); return url ? `<img src="${this._esc(url)}" class="thumb"/>` : `<div class="thumb placeholder"></div>`; }
  _pct(p){ return Math.min(100, ((Number(p.collected)||0) / Math.max(1, Number(p.goal)||1))*100 ) || 0; }
  _esc(s){ return String(s ?? '').replace(/[&<>"']/g, (c)=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c])); }
  _openModal(html){ return (window.openModal ? window.openModal(html) : { panel: document.body }); }
  _closeModal(){ if(window.__closeModal) window.__closeModal(); }
}