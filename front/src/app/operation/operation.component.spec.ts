import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { Observable, of, throwError } from 'rxjs';
import { AccountBankResponseDTO } from 'src/interface/accountBankResponseDTO';
import { RecuResponseDTO } from 'src/interface/recuResponseDTO';
import { ApiService } from '../services/api.service';
import { StorageService } from '../services/storage.service';
import { OperationComponent } from './operation.component';

describe('OperationComponent', () => {
  let component: OperationComponent;
  let fixture: ComponentFixture<OperationComponent>;
  let router: Router;
  let apiService: ApiService;
  let storageService: StorageService;


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, HttpClientModule,  MatOptionModule, MatSelectModule,BrowserAnimationsModule],
      declarations: [ OperationComponent ],
      providers: [FormBuilder],
    })
    .compileComponents();

    fixture = TestBed.createComponent(OperationComponent);
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

  it('should controle nrecup account bank id', waitForAsync(()=>{  
    const component = fixture.componentInstance;    
    storageService.saveData('token', "test_token");
    storageService.setaccountBankIds([3]);
    let accountBankResponseDTO : AccountBankResponseDTO = new AccountBankResponseDTO(2339,232323);
    accountBankResponseDTO.id = 3
    let accountBankResponses :AccountBankResponseDTO[] =[accountBankResponseDTO];
    let observableAccountBankResponseDTOs : Observable<AccountBankResponseDTO[]> = of(accountBankResponses);
    spyOn(apiService, "getAccountBankAll").and.returnValue(observableAccountBankResponseDTOs);
    component.ngOnInit();
    expect(component.accountBankIds).toEqual([3]);
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
  
  it('should controle create operation Valid', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    let accountBankId = 3;
    let somme = 2314;
    let typeOperation = "DEPOSIT";
    component.operationForm.patchValue({
      accountBankId: accountBankId,
      somme: somme,
      typeOperation: typeOperation,
    });
    component.accountBankIds = [3];
    const navigateSpy = spyOn(router, 'navigate');
    let observableRecuResponseDTO : Observable<RecuResponseDTO> = of(new RecuResponseDTO());
    spyOn(apiService, "nouvelleOperation").and.returnValue(observableRecuResponseDTO);
    component.onFormSubmitOperation();
    expect(navigateSpy).toHaveBeenCalledWith(['/welcome']);
  }));

  it('should controle create account Bank account bank id empty', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.operationForm.patchValue({
      accountBankId: "",
      somme: 2342,
      typeOperation: "DEPOSIT",
    });  
    component.accountBankIds = [3];
    component.onFormSubmitOperation();
    expect(component.listErrorAccountBankId).toEqual(["le champs n'est pas remplit"]);
  }));

  it('should controle create account Bank somme empty', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.operationForm.patchValue({
      accountBankId: 3,
      somme: "",
      typeOperation: "DEPOSIT",
    });  
    component.accountBankIds = [3];
    component.onFormSubmitOperation();
    expect(component.listErrorSomme).toEqual(["le champs n'est pas remplit"]);
  }));

  it('should controle create account Bank type operation empty', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.operationForm.patchValue({
      accountBankId: 3,
      somme: 2352,
      typeOperation: "",
    });  
    component.accountBankIds = [3];
    component.onFormSubmitOperation();
    expect(component.listErrorTypeOperation).toEqual(["le champs n'est pas remplit"]);
  }));

  it('should controle create account Bank type operation empty', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.operationForm.patchValue({
      accountBankId: 3,
      somme: 2352,
      typeOperation: "",
    });  
    component.accountBankIds = [3];
    component.onFormSubmitOperation();
    expect(component.listErrorTypeOperation).toEqual(["le champs n'est pas remplit"]);
  }));

  it('should controle create account Bank somme negative', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.operationForm.patchValue({
      accountBankId: 3,
      somme: -43245,
      typeOperation: "DEPOSIT",
    });  
    component.accountBankIds = [3];
    component.onFormSubmitOperation();
    expect(component.listErrorSomme).toEqual(["le minimum est 1 et le maximum est 1000000000"]);
  }));
});
