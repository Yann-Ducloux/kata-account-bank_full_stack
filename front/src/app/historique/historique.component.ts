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
  constructor(private apiService: ApiService, private storageService:StorageService, private router: Router) { }
  historique !:HistoriqueOperationDTO;
  operationLightDTO :OperationLightDTO[] =[];
  accountBankId:number | undefined;
  ngOnInit(): void {
    this.recupHisto();
  }
  recupHisto() {
    this.accountBankId = this.storageService.getaccountBankId();
    if(this.accountBankId!=null && this.accountBankId!= undefined){
      this.apiService.getHistorique(this.accountBankId).subscribe({
        next: (response) => {
          this.historique = response;
          this.operationLightDTO = response.operationLightDTO
        },
        error: (error) => {
          alert(error.error);
        }
      });
    } else { 
      this.toHome();
    }
  }
  toHome(){
    this.router.navigate(['/welcome']);
  }
  deconnection() {
    this.storageService.clearData();
    this.router.navigate(['/']);
  }
}
