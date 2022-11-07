export class  ClientResponseDTO {
    public mail?: String;
    public nom?: String;
    public prenom?: String;
    constructor (mail: String, nom: String, prenom: String){
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
    }
}