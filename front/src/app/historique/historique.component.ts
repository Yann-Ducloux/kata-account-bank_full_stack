import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HistoriqueOperationDTO } from 'src/interface/historiqueOperationDTO';
import { OperationLightDTO } from 'src/interface/operationLightDTO';
import { ApiService } from '../services/api.service';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-historique',
  templateUrl: './historique.component.html',
  styleUrls: ['./historique.component.scss']
})
export class HistoriqueComponent implements OnInit {
  displayedColumns: string[] = ['somme', 'date'];
  constructor( private router: Router, private storageService:StorageService,  private apiService: ApiService,) { }
  historique !:HistoriqueOperationDTO;
  operationLightDTO :OperationLightDTO[] =[];
  accountBankId:number | undefined;
  compteExist: boolean = false;
  ngOnInit(): void {
    if(this.apiService.getToken() == null || this.apiService.getToken() == undefined|| this.apiService.getToken() == "") {
      this.deconnection();
    }
    this.recupHisto();
  }
  
  recupHisto() {
    this.accountBankId = this.storageService.getaccountBankId();
    this.compteExist = false;
    if(!Number.isNaN(this.accountBankId) && this.accountBankId!=undefined){
      this.apiService.getHistorique(this.accountBankId).subscribe({
        next: (response) => {
          this.historique = response;
          this.operationLightDTO = response.operationLightDTO;
          this.compteExist = true;
        },
        error: (error) => {        
          alert(error.error);
        }
      });
    } else { 
      alert("le compte n'est pas bon");
    }
  }
  toHome() {
    this.router.navigate(['/welcome']);
  }
  deconnection() {
    this.storageService.clearData();
    this.router.navigate(['/']);
  }
}
