import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, of, throwError } from 'rxjs';
import { AccountBankResponseDTO } from 'src/interface/accountBankResponseDTO';
import { ApiService } from '../services/api.service';
import { StorageService } from '../services/storage.service';

import { WelcomeComponent } from './welcome.component';

describe('WelcomeComponent', () => {
  let component: WelcomeComponent;
  let fixture: ComponentFixture<WelcomeComponent>;
  let router: Router;
  let apiService: ApiService;
  let storageService: StorageService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, HttpClientModule],
      declarations: [ WelcomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WelcomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
    apiService = TestBed.inject(ApiService);
    storageService = TestBed.inject(StorageService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });



  it('should controle navigate account bank', waitForAsync(()=>{  
    const component = fixture.componentInstance;    
    const navigateSpy = spyOn(router, 'navigate');
    component.goToAccountBank();
    expect(navigateSpy).toHaveBeenCalledWith(['/accountBank']);
  }));

  it('should controle navigate historique', waitForAsync(()=>{  
    const component = fixture.componentInstance;    
    const navigateSpy = spyOn(router, 'navigate');
    let accountBankResponseDTO: AccountBankResponseDTO = new AccountBankResponseDTO(1000, 2190);
    accountBankResponseDTO.id = 4;
    component.goToHistorique(accountBankResponseDTO);
    expect(navigateSpy).toHaveBeenCalledWith(['/historique']);
  }));

  it('should controle navigate deconnection', waitForAsync(()=>{  
    const component = fixture.componentInstance;    
    const navigateSpy = spyOn(router, 'navigate');
    storageService.saveData('token', "test_token");
    component.deconnection();
    expect(navigateSpy).toHaveBeenCalledWith(['/']);
    expect(storageService.getData('token')).toEqual(null);
  }));

  it('should controle navigate operation', waitForAsync(()=>{  
    const component = fixture.componentInstance;    
    const navigateSpy = spyOn(router, 'navigate');
    let accountBankResponseDTO: AccountBankResponseDTO = new AccountBankResponseDTO(1000, 2190);
    accountBankResponseDTO.id = 4;
    component.accountBankResponse = [accountBankResponseDTO]
    component.goToOperation();
    expect(navigateSpy).toHaveBeenCalledWith(['/operation']);
    expect(storageService.getaccountBankIds()).toEqual([4]);
  }));

  it('should controle ngOntinit deconnection', waitForAsync(()=>{  
    const component = fixture.componentInstance;    
    const navigateSpy = spyOn(router, 'navigate');
    storageService.saveData('token', "");
    component.ngOnInit();
    expect(navigateSpy).toHaveBeenCalledWith(['/']);
    expect(storageService.getData('token')).toEqual(null);
  }));

  
  it('should controle ngOntinit', waitForAsync(()=>{  
    const component = fixture.componentInstance;    
    storageService.saveData('token', "test_token");
    let accountBankResponseDTO : AccountBankResponseDTO = new AccountBankResponseDTO(2339,232323);
    accountBankResponseDTO.id = 3
    let accountBankResponses :AccountBankResponseDTO[] =[accountBankResponseDTO];
    let observableAccountBankResponseDTOs : Observable<AccountBankResponseDTO[]> = of(accountBankResponses);
    spyOn(apiService, "getAccountBankAll").and.returnValue(observableAccountBankResponseDTOs);
    component.ngOnInit();
    expect(component.accountBankResponse).toEqual(accountBankResponses);
  }));

  
  it('should controle ngOntinit error', waitForAsync(()=>{  
    const component = fixture.componentInstance;    
    storageService.saveData('token', "test_token");
    let accountBankResponseDTO : AccountBankResponseDTO = new AccountBankResponseDTO(2339,232323);
    accountBankResponseDTO.id = 3
    let messageError = "Erreur inconnue";
    const errorResponse = new HttpErrorResponse({
      error: messageError,
      status: 400,
      statusText: 'OK',
    });  
    spyOn(apiService, "getAccountBankAll").and.returnValue(throwError(() => errorResponse));
    spyOn(window, "alert");
    component.ngOnInit();
    expect(window.alert).toHaveBeenCalledWith(messageError);
  }));
});
