import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Observable, of, throwError } from 'rxjs';
import { AccountBankResponseDTO } from 'src/interface/accountBankResponseDTO';
import { ApiService } from '../services/api.service';
import { StorageService } from '../services/storage.service';

import { AccountBankComponent } from './account-bank.component';

describe('AccountBankComponent', () => {
  let component: AccountBankComponent;
  let fixture: ComponentFixture<AccountBankComponent>;
  let router: Router;
  let apiService: ApiService;
  let storageService: StorageService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, HttpClientModule],
      declarations: [ AccountBankComponent ],
      providers: [FormBuilder],
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountBankComponent);
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

  it('should controle create account Bank error inconnue', waitForAsync(()=>{  

    let messageError = "erreur inconnue";
    const errorResponse = new HttpErrorResponse({
      error: messageError,
      status: 400,
      statusText: 'OK',
   });
    const component = fixture.componentInstance;
    let solde = 1000;
    let decouvert= 128322;
    component.accountBankForm.patchValue({
      solde: solde,
      decouvert: decouvert,
    });
    spyOn(window, "alert");
    spyOn(apiService, "createAccountBank").and.returnValue(throwError(() => errorResponse));
    component.onFormSubmitAccountBank();
    expect(window.alert).toHaveBeenCalledWith(messageError);
  }));


  it('should controle create account Bank negative solde', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.accountBankForm.patchValue({
      solde: -1000,
      decouvert: 128322,
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="solde"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitAccountBank();
    expect(component.listErrorSolde).toEqual(["le minimum et 0 et le maximum est 1000000000"]);
  }));

  it('should controle create account Bank too heavy decouvert', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.accountBankForm.patchValue({
      solde: 1000,
      decouvert: 1000839000,
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="solde"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitAccountBank();
    expect(component.listErrorDecouvert).toEqual(["le minimum et 0 et le maximum est 1000000000"]);
  }));


  it('should controle create account Bank solde empty', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.accountBankForm.patchValue({
      solde: "",
      decouvert: 100000,
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="solde"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitAccountBank();
    expect(component.listErrorSolde).toEqual(["le champs n'est pas remplit"]);
  }));

  it('should controle create account Bank nan decouvert', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.accountBankForm.patchValue({
      solde: 1000,
      decouvert: "djsqh",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="solde"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitAccountBank();
    expect(component.listErrorDecouvert).toEqual(["le champ est incorrecte"]);
  }));
});
