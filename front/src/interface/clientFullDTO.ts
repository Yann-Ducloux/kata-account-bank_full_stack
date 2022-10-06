export class  ClientFullDTO {
    public mail?: String;
    public nom?: String;
    public prenom?: String;
    public password?: String;
    constructor (mail: String, nom: String, prenom: String, password: String){
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
    }
}