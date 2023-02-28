import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountBankResponseDTO } from 'src/interface/accountBankResponseDTO';
import { OperationRequestDTO } from 'src/interface/operationRequestDTO';
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
  constructor(private formBuilder: FormBuilder, private storageService: StorageService, private apiService: ApiService, private router: Router) { }
  ngOnInit(): void {
    if (this.apiService.getToken() == null || this.apiService.getToken() == undefined) {
      this.deconnection();
    }
    this.operationForm = this.formBuilder.group({
      accountBankId: ['', [Validators.required]],
      somme: ['', [Validators.required]],
      typeOperation: ['', [Validators.required]],
    });
    this.accountBankIds = this.storageService.getaccountBankIds();
    if (this.accountBankIds == null || this.accountBankIds == undefined || this.accountBankIds.length == 0) {
      this.recupAccountBankAll();
    }
  }
  listErrorAccountBankId: String[] = [];
  listErrorSomme: String[] = [];
  listErrorTypeOperation: String[] = [];
  toHome() {
    this.router.navigate(['/welcome']);
  }

  onFormSubmitOperation() {
    this.listErrorSomme = [];
    if (this.operationForm.get('accountBankId')?.valid && this.controleChampInputAccountBank() &&
      !this.isInvalidAndDirty('somme') && this.controleChampSomme() &&
      this.operationForm.get('typeOperation')?.valid && this.controleChampInputTypeOperation()) {
      this.envoieDonnee();
    } else {
      this.listErrorAccountBankId = this.recupChampInputError(this.controleChampInputAccountBank());
      this.listErrorSomme = this.recupChampSommeError();
      this.listErrorTypeOperation = this.recupChampInputError(this.controleChampInputTypeOperation());
    }
  }
  controleChampInputAccountBank(): boolean | undefined {
    return this.operationForm.get('accountBankId')?.value != null && this.operationForm.get('accountBankId')?.value != ""
      && !isNaN(Number(this.operationForm.get('accountBankId')?.value))
      && this.accountBankIds?.includes(Number(this.operationForm.get('accountBankId')?.value));
  }
  controleChampSomme(): boolean {
    return this.operationForm.get('somme')?.value != null && this.operationForm.get('somme')?.value != ""
      && !isNaN(Number(this.operationForm.get('somme')?.value))
      && Number(this.operationForm.get('somme')?.value) > 0 && Number(this.operationForm.get('somme')?.value) <= 1000000000;
  }
  controleChampInputTypeOperation(): boolean | undefined {
    return this.operationForm.get('typeOperation')?.value == "DEPOSIT" || this.operationForm.get('typeOperation')?.value == "WITHDRAWAL";
  }
  recupChampInputError(control: boolean | undefined): string[] {
    let listError: string[] = [];
    if (!control) {
      listError.push("le champs n'est pas remplit");
    }
    return listError;
  }
  recupChampSommeError(): string[] {
    let listError: string[] = [];
    if (this.operationForm.get('somme')?.value == null || this.operationForm.get('somme')?.value == "") {
      listError.push("le champs n'est pas remplit");
    } else if (isNaN(Number(this.operationForm.get('somme')?.value))) {
      listError.push("le champ est incorrecte");
    } else if (Number(this.operationForm.get('somme')?.value) < 1 || Number(this.operationForm.get('somme')?.value) > 1000000000) {
      listError.push("le minimum est 1 et le maximum est 1000000000");
    }
    return listError;
  }
  isInvalidAndDirty(field: string): boolean {
    const ctrl = this.operationForm.get(field);
    return ctrl !== null && !ctrl.valid && ctrl.dirty && isNaN(Number(field));
  }
  envoieDonnee() {
    let operationRequestDTO = new OperationRequestDTO(this.operationForm.get('accountBankId')?.value, Number(this.operationForm.get('somme')?.value), this.operationForm.get('typeOperation')?.value);
    this.apiService.nouvelleOperation(operationRequestDTO).subscribe({
      next: (response) => {
        this.toHome();
      },
      error: (error) => {
        alert(error.error);
      }
    });
  }

  recupAccountBankAll() {
    this.accountBankIds = [];
    this.apiService.getAccountBankAll().subscribe({
      next: (accountBanks: AccountBankResponseDTO[]) => {
        accountBanks.forEach(accountBank => {
          if (accountBank != null && accountBank.id != null && this.accountBankIds != null) {
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
