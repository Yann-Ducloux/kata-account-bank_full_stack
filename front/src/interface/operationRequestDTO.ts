import { TypeOperation } from "./typeOperation";

export class  OperationRequestDTO {
    public idAccountBank?: number;
    public somme?: number;
    public typeOperation?: TypeOperation;
    constructor (idAccountBank: number, somme: number ,typeOperation: TypeOperation){
        this.idAccountBank = idAccountBank;
        this.somme = somme;
        this.typeOperation = typeOperation;
    }
}