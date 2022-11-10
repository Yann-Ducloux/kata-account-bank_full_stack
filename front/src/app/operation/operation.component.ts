import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../services/api.service';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-operation',
  templateUrl: './operation.component.html',
  styleUrls: ['./operation.component.scss']
})
export class OperationComponent implements OnInit {
  public operationForm!: FormGroup;
  constructor(private formBuilder: FormBuilder, private storageService:StorageService,private apiService: ApiService, private router: Router) { }
  ngOnInit(): void {
    if(this.router.getCurrentNavigation()!=null && this.router.getCurrentNavigation()!=undefined) {
      console.log(this.router.getCurrentNavigation()?.extras);
    }
    this.operationForm = this.formBuilder.group({
      somme: ['', [Validators.required]],
    });
  }
  listErrorSomme: String[]= [];
  listErrorDecouvert: String[]= [];
  toHome(){
    this.router.navigate(['/welcome']);
  }
  
  onFormSubmitOperation() {
    this.listErrorSomme = [];
    if(!this.isInvalidAndDirty('solde') && this.operationForm.get('solde')?.value!="") {
      this.envoieDonnee();
    } else {
      this.listErrorSomme = this.controleChamp(this.operationForm.get('solde')?.value); 
    }
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
  
  deconnection() {
    this.storageService.clearData();
    this.router.navigate(['/']);
  }
}
