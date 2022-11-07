import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountBankRequestDTO } from 'src/interface/accountBankRequestDTO';
import { ApiService } from '../services/api.service';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent implements OnInit {

  constructor( private router: Router, private storageService:StorageService,  private apiService: ApiService,) { }

  ngOnInit(): void {
    console.log('test: '+ this.storageService.getData('token'));
    var accountBankRequestDTO = new AccountBankRequestDTO(200,1000);
    console.log('yann');
    this.apiService.createAccountBank(accountBankRequestDTO)/*.subscribe({
      next: (response) => {
      alert(response);
//      this.router.navigate(['/authentification']);
    },
    error: (error) => {
      alert(error.error);
    }
  });*/
  }
}
