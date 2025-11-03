
// POO Notificaciones: list, add, markAllRead. Persistencia por usuario: KS_NOTIF_<userId>
class Notificaciones{
  constructor(prefix='KS_NOTIF_'){ this.prefix = prefix; }
  _key(uid){ return this.prefix + String(uid); }
  list(uid){
    try{ const raw = localStorage.getItem(this._key(uid)); return raw ? JSON.parse(raw) : []; }
    catch(e){ return []; }
  }
  add(uid, notif){
    try{
      const arr = this.list(uid);
      arr.unshift(Object.assign({ id: Date.now(), read: false, createdAt: new Date().toISOString() }, notif));
      localStorage.setItem(this._key(uid), JSON.stringify(arr));
      return true;
    }catch(e){ return false; }
  }
  markAllRead(uid){
    try{
      const arr = this.list(uid).map(n=>Object.assign({}, n, {read:true}));
      localStorage.setItem(this._key(uid), JSON.stringify(arr));
      return true;
    }catch(e){ return false; }
  }
}
