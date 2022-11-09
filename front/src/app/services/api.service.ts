import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ErrorHandler, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AccountBankRequestDTO } from 'src/interface/accountBankRequestDTO';
import { AccountBankResponseDTO } from 'src/interface/accountBankResponseDTO';
import { ClientDTO } from 'src/interface/clientDTO';
import { ClientFullDTO } from 'src/interface/clientFullDTO';
import { HistoriqueOperationDTO } from 'src/interface/historiqueOperationDTO';
import { InfoUtilisateur } from 'src/interface/infoUtilisateur';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient, private errorhandler: ErrorHandler,
    private storageService: StorageService) { }
  createClient(client: ClientFullDTO): Observable<any> {
    return this.http.post<ClientDTO>(environment.baseUrl+'/client', client);
  }
  
  createAccountBank(accountBank: AccountBankRequestDTO): Observable<any> 
  {
    let auth_token = this.getToken();   
    const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer '+ auth_token,
      });
    const requestOptions = { headers: headers };  
    return this.http.post<AccountBankResponseDTO>(environment.baseUrl+'/account_bank', accountBank,requestOptions);
  }
  
  getHistorique(): Observable<any> 
  {
    let auth_token = this.getToken();    
    const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer '+ auth_token,
      });
    const requestOptions = { headers: headers };  
    return this.http.get<HistoriqueOperationDTO[]>(environment.baseUrl+'/historique/2', requestOptions);
  }
  
  getAccountBankAll(): Observable<any> 
  {
    let auth_token = this.getToken();    
    const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer '+ auth_token,
      });
    const requestOptions = { headers: headers };  
    return this.http.get<AccountBankResponseDTO[]>(environment.baseUrl+'/account_bank/find', requestOptions);
  }

  authentification(infoUtilisateur: InfoUtilisateur): Observable<any> {
    return this.http.post<String>(environment.baseUrl+'/authentification', infoUtilisateur);
  }
  getToken(){
    return this.storageService.getData('token');
  }
}
