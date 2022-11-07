export class  AccountBankRequestDTO {
    public solde?: number;
    public decouvert?: number;
    constructor (solde: number, decouvert: number){
        this.solde = solde;
        this.decouvert = decouvert;
    }
}