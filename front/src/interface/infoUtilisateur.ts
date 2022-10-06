export class  InfoUtilisateur {
    public mail?: String;
    public password?: String;
    constructor (mail: String, password: String){
        this.mail = mail;
        this.password = password;
    }
}