import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { Observable, of, throwError } from 'rxjs';
import { HistoriqueOperationDTO } from 'src/interface/historiqueOperationDTO';
import { OperationLightDTO } from 'src/interface/operationLightDTO';
import { TypeOperation } from 'src/interface/typeOperation';
import { ApiService } from '../services/api.service';
import { StorageService } from '../services/storage.service';
import { HistoriqueComponent } from './historique.component';

describe('HistoriqueComponent', () => {
  let component: HistoriqueComponent;
  let fixture: ComponentFixture<HistoriqueComponent>;
  let router: Router;
  let apiService: ApiService;
  let storageService: StorageService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, 
        MatCardModule,BrowserAnimationsModule, MatTableModule,
        MatIconModule,],
      declarations: [ HistoriqueComponent ],
      providers: [FormBuilder],
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistoriqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
    apiService = TestBed.inject(ApiService);
    storageService = TestBed.inject(StorageService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should controle navigate deconnection', waitForAsync(()=>{  
    const component = fixture.componentInstance;    
    const navigateSpy = spyOn(router, 'navigate');
    storageService.setaccountBankId(3);
    storageService.saveData('token', "test_token");
    component.deconnection();
    expect(navigateSpy).toHaveBeenCalledWith(['/']);
    expect(storageService.getData('token')).toEqual(null);
  }));

  it('should controle navigate home', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    const navigateSpy = spyOn(router, 'navigate');
    component.ngOnInit();
    component.toHome();
    expect(navigateSpy).toHaveBeenCalledWith(['/welcome']);
  }));

  it('should controle recup historique', waitForAsync(()=>{  
    const component = fixture.componentInstance;    
    storageService.saveData('token', "test_token");
    storageService.setaccountBankId(3);
    let historiqueOperationDTO : HistoriqueOperationDTO = new HistoriqueOperationDTO();
    let operationLightDTO: OperationLightDTO = new OperationLightDTO();
    operationLightDTO.dateOperation= new Date();
    operationLightDTO.somme = 1809;
    operationLightDTO.typeOperation = TypeOperation.DEPOSIT;
    
    let operationLightDTOs: OperationLightDTO[] = [operationLightDTO];
    historiqueOperationDTO.accountBankid = 3;
    historiqueOperationDTO.solde = 6879;
    historiqueOperationDTO.decouvert = 1000;
    historiqueOperationDTO.operationLightDTO = operationLightDTOs;

    let observableHistoriqueOperationDTO : Observable<HistoriqueOperationDTO> = of(historiqueOperationDTO);
    spyOn(apiService, "getHistorique").and.returnValue(observableHistoriqueOperationDTO);
    component.ngOnInit();
    expect(component.compteExist).toBeTrue();
    expect(component.historique).toEqual(historiqueOperationDTO);
    expect(component.operationLightDTO).toEqual(historiqueOperationDTO.operationLightDTO);
  }));

  it('should controle not token', waitForAsync(()=>{  
    const navigateSpy = spyOn(router, 'navigate');
    storageService.saveData('token', "");
    const component = fixture.componentInstance;    
    component.ngOnInit();
    expect(navigateSpy).toHaveBeenCalledWith(['/']);
  }));

  it('should controle  historique failed', waitForAsync(()=>{  
    const component = fixture.componentInstance;    
    storageService.saveData('token', "test_token");
    storageService.setaccountBankId(NaN); 
    spyOn(window, "alert");
    component.ngOnInit();
    expect(component.compteExist).toBeFalse();
    expect(window.alert).toHaveBeenCalledWith("le compte n'est pas bon");
  }));


  it('should controle submitUser password erreur inconnue', waitForAsync(()=>{  


    let messageError = "erreur inconnue";
    const errorResponse = new HttpErrorResponse({
      error: messageError,
      status: 400,
      statusText: 'OK',
   });
    const component = fixture.componentInstance;    
    storageService.saveData('token', "test_token");
    storageService.setaccountBankId(3);
    spyOn(apiService, "getHistorique").and.returnValue(throwError(() => errorResponse));
    spyOn(window, "alert");
    component.ngOnInit();
    expect(component.compteExist).toBeFalse();
    expect(window.alert).toHaveBeenCalledWith(messageError);
  }));

});