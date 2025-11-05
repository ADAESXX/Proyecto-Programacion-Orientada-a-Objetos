
// POO: Mensajes por usuario. Persistencia en LocalStorage.
class Mensajes{
  constructor(prefix='KS_MSG_'){ this.prefix = prefix; }
  _inboxKey(uid){ return this.prefix + String(uid); }
  _sentKey(uid){ return 'KS_MSG_SENT_' + String(uid); }

  listInbox(uid){
    try{ const raw = localStorage.getItem(this._inboxKey(uid)); return raw? JSON.parse(raw) : []; }
    catch(e){ return []; }
  }
  listSent(uid){
    try{ const raw = localStorage.getItem(this._sentKey(uid)); return raw? JSON.parse(raw) : []; }
    catch(e){ return []; }
  }
  _saveInbox(uid, arr){ localStorage.setItem(this._inboxKey(uid), JSON.stringify(arr)); }
  _saveSent(uid, arr){ localStorage.setItem(this._sentKey(uid), JSON.stringify(arr)); }

  send(fromUser, toUser, text){
    const msg = {
      id: Date.now(),
      fromId: String(fromUser.id),
      fromName: fromUser.nombre,
      toId: String(toUser.id),
      toName: toUser.nombre,
      text: String(text||'').trim(),
      createdAt: new Date().toISOString(),
      read: false
    };
    // Guarda en inbox del receptor
    const inbox = this.listInbox(toUser.id);
    inbox.unshift(msg);
    this._saveInbox(toUser.id, inbox);
    // Guarda copia en enviados del emisor
    const sent = this.listSent(fromUser.id);
    sent.unshift(msg);
    this._saveSent(fromUser.id, sent);
    return msg;
  }

  markAllRead(uid){
    const inbox = this.listInbox(uid).map(m => Object.assign({}, m, {read:true}));
    this._saveInbox(uid, inbox);
    return true;
  }
}
