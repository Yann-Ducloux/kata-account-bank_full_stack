import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ErrorHandler, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AccountBankRequestDTO } from 'src/interface/accountBankRequestDTO';
import { AccountBankResponseDTO } from 'src/interface/accountBankResponseDTO';
import { ClientDTO } from 'src/interface/clientDTO';
import { ClientFullDTO } from 'src/interface/clientFullDTO';
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
  
  createAccountBank(accountBank: AccountBankRequestDTO): Observable<any> {
    let auth_token = this.getToken();    
    console.log('yann2');
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
        'Authorization': 'Bearer '+ auth_token,
      });
    const requestOptions = { headers: headers };  
    console.log(accountBank); 
    console.log(auth_token);
    console.log(headers);
    console.log(requestOptions);
    return this.http.post<AccountBankResponseDTO>(environment.baseUrl+'/account_bank', accountBank,requestOptions);
  }

  authentification(infoUtilisateur: InfoUtilisateur): Observable<any> {
    return this.http.post<String>(environment.baseUrl+'/authentification', infoUtilisateur);
  }
  getToken(){
    return this.storageService.getData('token');
  }
}
