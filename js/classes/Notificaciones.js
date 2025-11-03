
// POO Notificaciones (solo lectura y marcar como leídas)
class Notificaciones{
  constructor(prefix='KS_NOTIF_'){ this.prefix = prefix; }
  _key(uid){ return this.prefix + String(uid); }
  list(uid){
    try{ const raw = localStorage.getItem(this._key(uid)); return raw ? JSON.parse(raw) : []; }
    catch(e){ return []; }
  }
  markAllRead(uid){
    try{ const arr = this.list(uid).map(n=>({...n, read:true})); localStorage.setItem(this._key(uid), JSON.stringify(arr)); return true; }
    catch(e){ return false; }
  }
}
