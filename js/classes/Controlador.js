// Controlador.js — Orquestación con localStorage. No usa API.
class Controlador {
  constructor(vista){
    this.vista = vista;
    this.KU = 'ks_users'; this.KP = 'ks_projects'; this.KS = 'ks_session';
  }
  init(){
    this.vista.bindGlobalButtons({
      onOpenNotifications: () => this.openNotifications(),
      onOpenMessages: () => this.openMessages()
    });
    this._renderCurrent();
  }
  _renderCurrent(){
    const projects = this._getProjects();
    const onDonate  = (id)=> this._handleDonate(id);
    const onDetails = (id)=> this._handleDetails(id);
    if (document.body.classList.contains('page-proyectos') || document.getElementById('projects-list-root')) {
      this.vista.renderLista(projects, { onDonate, onDetails });
    } else {
      this.vista.renderPortada(projects, { onDonate, onDetails });
    }
  }
  _handleDonate(projectId){
    const p = this._getProjects().find(x => String(x.id) == String(projectId));
    if(!p) return;
    if(typeof window.openDonateModal === 'function'){
      window.openDonateModal(p);
      setTimeout(()=> this._renderCurrent(), 350);
    }
  }
  _handleDetails(projectId){
    const p = this._getProjects().find(x => String(x.id) == String(projectId));
    if(!p) return;
    if(typeof window.openDetailsModal === 'function'){
      window.openDetailsModal(p);
      setTimeout(()=> this._renderCurrent(), 350);
    }
  }
  openNotifications(){
    if(typeof window.openNotifModal === 'function'){ window.openNotifModal(); return; }
    const ses = this._getSession();
    if(!ses){ this._simpleModal('<h3>Notificaciones</h3><div>Inicia sesión para verlas.</div>'); return; }
    let items = [];
    try{ if(typeof Notificaciones==='function'){ items = new Notificaciones().list(ses.id); } }catch(e){}
    this.vista.showNotifications(items, { onMarkAllRead: () => { try{ new Notificaciones().markAllRead(ses.id); }catch(e){} } });
  }
  openMessages(){
    if(typeof window.openMessagesModal === 'function'){ window.openMessagesModal(); return; }
    this._simpleModal('<h3>Mensajes</h3><div>Próximamente…</div>');
  }
  _getProjects(){ try{ return JSON.parse(localStorage.getItem(this.KP) || '[]'); }catch(e){ return []; } }
  _getSession(){ try{ return JSON.parse(localStorage.getItem(this.KS) || 'null'); }catch(e){ return null; } }
  _simpleModal(content){
    const html = content + '<div style="display:flex;justify-content:flex-end;margin-top:12px"><button class="btn btn-primary" onclick="__closeModal()">Cerrar</button></div>';
    if(window.openModal) openModal(html);
  }
}