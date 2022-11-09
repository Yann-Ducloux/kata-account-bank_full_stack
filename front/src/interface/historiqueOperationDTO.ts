import { OperationLightDTO } from "./operationLightDTO";

export class  HistoriqueOperationDTO {
    
    public accountBankid?: number;
    public solde?: number;
    public decouvert?: number;
    public operationLightDTO?: OperationLightDTO[];
}