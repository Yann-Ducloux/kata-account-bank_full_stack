import { Component, OnInit } from '@angular/core';
import { HistoriqueOperationDTO } from 'src/interface/historiqueOperationDTO';
import { OperationLightDTO } from 'src/interface/operationLightDTO';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-historique',
  templateUrl: './historique.component.html',
  styleUrls: ['./historique.component.scss']
})
export class HistoriqueComponent implements OnInit {
  displayedColumns: string[] = ['somme', 'date'];
  constructor(private apiService: ApiService,) { }
  historique !:HistoriqueOperationDTO;
  operationLightDTO :OperationLightDTO[] =[];
  ngOnInit(): void {
      this.apiService.getHistorique().subscribe({
        next: (response) => {
          this.historique = response;
          console.log(this.historique);
          console.log(this.historique.operationLightDTO);
          this.operationLightDTO = response.operationLightDTO
          console.log(this.operationLightDTO);
        },
        error: (error) => {
          alert(error.error);
        }
      });
  }
}
