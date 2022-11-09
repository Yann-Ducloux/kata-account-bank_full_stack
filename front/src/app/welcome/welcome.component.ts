import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
  /*  console.log('test: '+ this.storageService.getData('token'));
    var accountBankRequestDTO = new AccountBankRequestDTO(200,1000);
    console.log('yann');
    this.apiService.createAccountBank(accountBankRequestDTO).subscribe({
      next: (response) => {
      console.log(response);
      
    },
    error: (error) => {
      alert(error.error);
    }
  });
*/

  this.apiService.getAccountBankAll().subscribe({
    next: (response) => {
      this.accountBankResponse = response;
      console.log(this.accountBankResponse[0]);
  },
  error: (error) => {
    alert(error.error);
  }
});
  }

  

  getToHistorique(row:any){
    this.router.navigate(['/historique']);
  }
}
