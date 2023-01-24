import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountBankRequestDTO } from 'src/interface/accountBankRequestDTO';
import { ApiService } from '../services/api.service';
import { StorageService } from '../services/storage.service';


@Component({
  selector: 'app-account-bank',
  templateUrl: './account-bank.component.html',
  styleUrls: ['./account-bank.component.scss']
})
export class AccountBankComponent implements OnInit {
  public accountBankForm!: FormGroup;
  constructor(private formBuilder: FormBuilder, private storageService:StorageService,private apiService: ApiService, private router: Router) { }
  ngOnInit(): void {
    if(this.apiService.getToken() == null || this.apiService.getToken() == undefined) {
      this.deconnection();
    } else {
      this.accountBankForm = this.formBuilder.group({
        solde: ['', [Validators.required]],
        decouvert: ['', [Validators.required]],
      });
    }
  }
  listErrorSolde: String[]= [];
  listErrorDecouvert: String[]= [];
  toHome(){
    this.router.navigate(['/welcome']);
  }
  
  onFormSubmitAccountBank() {
    this.listErrorSolde = [];
    this.listErrorDecouvert = [];   
    if(!this.isInvalidAndDirty('solde') && this.controleChamp(this.accountBankForm.get('solde')?.value)
    && !this.isInvalidAndDirty('decouvert') && this.controleChamp(this.accountBankForm.get('decouvert')?.value)) {
      this.envoieDonnee();
    } else {
      this.listErrorSolde = this.recupMessageError(this.accountBankForm.get('solde')?.value); 
      this.listErrorDecouvert = this.recupMessageError(this.accountBankForm.get('decouvert')?.value);
    }
  }
  controleChamp(champ: string): boolean {
    return champ !=null && champ != "" && !isNaN(Number(champ))
    && Number(champ)>=0 && Number(champ)<=1000000000;
  }
  recupMessageError(champ: string) :  String[]{
    var listError:String[] = [];
    if(champ ==null || champ == "" ) {
      listError.push("le champs n'est pas remplit");
    } else if(isNaN(Number(champ))){
      listError.push("le champ est incorrecte");
    } else if(Number(champ)<0 || Number(champ)>1000000000) {
      listError.push("le minimum et 0 et le maximum est 1000000000");
    }
    return listError;
  }
  isInvalidAndDirty(field: string): boolean {
    const ctrl = this.accountBankForm.get(field);
    return ctrl !== null && !ctrl.valid && ctrl.dirty && isNaN(Number(field));
  }
  envoieDonnee() {
    var accountBankRequestDTO = new AccountBankRequestDTO(this.accountBankForm.get('solde')?.value,this.accountBankForm.get('decouvert')?.value);
    this.apiService.createAccountBank(accountBankRequestDTO).subscribe({
      next: (response) => {
      this.toHome();
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
