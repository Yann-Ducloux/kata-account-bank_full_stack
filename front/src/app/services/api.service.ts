import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ClientDTO } from 'src/interface/clientDTO';
import { ClientFullDTO } from 'src/interface/clientFullDTO';
import { InfoUtilisateur } from 'src/interface/infoUtilisateur';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }
  
  createClient(client: ClientFullDTO): Observable<any> {
    return this.http.post<ClientDTO>(environment.baseUrl+'/client', client);
  }
  
  authentification(infoUtilisateur: InfoUtilisateur): Observable<any> {
    return this.http.post<String>(environment.baseUrl+'/authentification', infoUtilisateur);
  }
}
