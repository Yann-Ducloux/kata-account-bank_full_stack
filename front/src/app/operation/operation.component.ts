import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountBankResponseDTO } from 'src/interface/accountBankResponseDTO';
import { ApiService } from '../services/api.service';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-operation',
  templateUrl: './operation.component.html',
  styleUrls: ['./operation.component.scss']
})
export class OperationComponent implements OnInit {
  public operationForm!: FormGroup;
  accountBankIds?: number[];
  constructor(private formBuilder: FormBuilder, private storageService:StorageService,private apiService: ApiService, private router: Router) { }
  ngOnInit(): void {
    this.accountBankIds = this.storageService.getaccountBankIds();
    console.log(this.accountBankIds);
    if(this.accountBankIds==null || this.accountBankIds==undefined|| this.accountBankIds.length==0) {
      this.recupAccountBankAll();
    }
    this.operationForm = this.formBuilder.group({
      accountBankId: ['', [Validators.required]],
      somme: ['', [Validators.required]],
      typeOperation: ['', [Validators.required]],
    });
  }
  listErrorAccountBankId: String[]= [];
  listErrorSomme: String[]= [];
  listErrorTypeOperation: String[]= [];
  toHome(){
    this.router.navigate(['/welcome']);
  }
  
  onFormSubmitOperation() {
    this.listErrorSomme = [];
    if(this.operationForm.get('accountBankId')?.valid ||
    !this.isInvalidAndDirty('somme') && this.operationForm.get('somme')?.value!="" ||
    this.operationForm.get('typeOperation')?.value !== "") {
      this.envoieDonnee();
    } else {
      this.listErrorAccountBankId = this.controleChampInput('accountBankId'); 
      this.listErrorSomme = this.controleChamp(this.operationForm.get('somme')?.value); 
      this.listErrorTypeOperation = this.controleChampInput('typeOperation');  
    }
  }
  controleChampInput(champ: string) :  String[]{
    var listError:String[] = [];
    if(!this.operationForm.get(champ)?.valid) {
      listError.push("le champs n'est pas remplit");
    }
    return listError;
  }
  controleChamp(champ: string) :  String[]{
    var listError:String[] = [];
    if(champ ==null || champ == "" ) {
      listError.push("le champs n'est pas remplit");
    } else if(isNaN(Number(champ))){
      listError.push("le champ est incorrecte");
    }else if(Number(champ)<0 || Number(champ)>1000000000) {
      listError.push("le minimum et 0 et le maximum est 1000000000");
    }
    return listError;
  }
  isInvalidAndDirty(field: string): boolean {
    const ctrl = this.operationForm.get(field);
    return ctrl !== null && !ctrl.valid && ctrl.dirty && isNaN(Number(field));
  }
  envoieDonnee() {
    console.log('test');
   /* var accountBankRequestDTO = new AccountBankRequestDTO(this.operationForm.get('solde')?.value,this.operationForm.get('decouvert')?.value);
    this.apiService.createAccountBank(accountBankRequestDTO).subscribe({
      next: (response) => {
      this.toHome();
    },
    error: (error) => {
      alert(error.error);
    }
  });
*/

  }
  

  recupAccountBankAll() {
    this.accountBankIds=[];
    this.apiService.getAccountBankAll().subscribe({
      next: (accountBanks: AccountBankResponseDTO[]) => {
        accountBanks.forEach(accountBank => {
          if( accountBank!=null && accountBank.id!=null && this.accountBankIds!=null) {
            this.accountBankIds.push(accountBank.id);
          }          
        });
    },
    error: (error) => {
      alert(error.error);
    }
  });
    }
  deconnection() {
    this.storageService.clearData();
    this.router.navigate(['/']);
  }
}
