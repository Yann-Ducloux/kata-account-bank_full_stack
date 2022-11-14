import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountBankRequestDTO } from 'src/interface/accountBankRequestDTO';
import { AccountBankResponseDTO } from 'src/interface/accountBankResponseDTO';
import { ApiService } from '../services/api.service';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent implements OnInit {
  displayedColumns: string[] = ['numero', 'solde', 'decouvert', 'date'];
  constructor( private router: Router, private storageService:StorageService,  private apiService: ApiService,) { }
  accountBankResponse :AccountBankResponseDTO[] =[];
  ngOnInit(): void {
    this.storageService.removeData('accountBankId');
    this.recupAccountBankAll();
  }

  recupAccountBankAll() {
    
  this.apiService.getAccountBankAll().subscribe({
    next: (response) => {
      this.accountBankResponse = response;
  },
  error: (error) => {
    alert(error.error);
  }
});
  }

  goToAccountBank(){
    this.router.navigate(['/accountBank']);
  }
  goToOperation(){
    var accountBankIds:number[]=[];
    this.accountBankResponse.forEach(accountBank => {
      if( accountBank!=null && accountBank.id!=null) {
        accountBankIds.push(accountBank.id);
      }
    });
    if(accountBankIds.length>0) {
      this.storageService.setaccountBankIds(accountBankIds);
      this.router.navigate(['/operation']);
    }
  }
  goToHistorique(accountBankResponseDTO:AccountBankResponseDTO){
    if(accountBankResponseDTO!=null && accountBankResponseDTO.id!=null) {
      this.storageService.setaccountBankId(accountBankResponseDTO.id);
      this.router.navigate(['/historique']);
    }
  }
  
  deconnection() {
    this.storageService.clearData();
    this.router.navigate(['/']);
  }
}
