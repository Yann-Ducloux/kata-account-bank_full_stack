import { ClientResponseDTO } from "./clientResponseDTO";

export class  AccountBankResponseDTO {
    public id?: number;
    public solde?: number;
    public decouvert?: number;
    public client?: ClientResponseDTO;
    public dateCreation?: Date;
    constructor (solde: number, decouvert: number){
        this.solde = solde;
        this.decouvert = decouvert;
    }
}