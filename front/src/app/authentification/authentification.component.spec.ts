import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Observable, of, throwError } from 'rxjs';
import { InfoUtilisateur } from 'src/interface/infoUtilisateur';
import { ApiService } from '../services/api.service';

import { AuthentificationComponent } from './authentification.component';

describe('AuthentificationComponent', () => {
  let component: AuthentificationComponent;
  let fixture: ComponentFixture<AuthentificationComponent>;
  let router: Router;
  let apiService: ApiService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, HttpClientModule],
      declarations: [ AuthentificationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthentificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
    apiService = TestBed.inject(ApiService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  
  it('should controle navigate home', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    const navigateSpy = spyOn(router, 'navigate');
    component.toHome();
    expect(navigateSpy).toHaveBeenCalledWith(['/']);
  }));

  it('should controle authentification', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.authentificationForm.patchValue({
      email: "ducloux.y@gmail.com",
      password: "password",
    });
    const navigateSpy = spyOn(router, 'navigate');
    let observableInfoUtilisateur : Observable<InfoUtilisateur> = of(new InfoUtilisateur("ducloux.y@gmail.com", "password"));
    spyOn(apiService, "authentification").and.returnValue(observableInfoUtilisateur);
    component.onFormSubmitAuthentification();
    expect(navigateSpy).toHaveBeenCalledWith(['/welcome']);
  }));

  
  it('should controle submitUser password false', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.authentificationForm.patchValue({
      email: "ducloux.y@gmail.com",
      password: "password",
    });
    let messageError = "le mail existe dèjà";
    const errorResponse = new HttpErrorResponse({
      error: messageError,
      status: 400,
      statusText: 'OK',
   });
    spyOn(apiService, "authentification").and.returnValue(throwError(() => errorResponse));
    spyOn(window, "alert");
    component.onFormSubmitAuthentification();
    expect(window.alert).toHaveBeenCalledWith(messageError);
  }));
  
  it('should controle mail meesage pattern', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.authentificationForm.patchValue({
      email: "ducloux.y",
      password: "password",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="email"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitAuthentification();
    expect(component.listErrorMail).toEqual(["Erreur pattern mail"]);
  }));

  it('should controle mail message empty', ()=>{
    component.authentificationForm.patchValue({
      email: "",
      password: "password",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="email"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitAuthentification();
    expect(component.listErrorMail).toEqual(["le champs n'est pas remplit"]);
  });

  it('should controle mail message lenght', ()=>{
    component.authentificationForm.patchValue({
      email: "sdfsdfsgthlkjfdfdlkjlkjfdlkjfdmkj@gmail.com",
      password: "password",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="email"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitAuthentification();
    expect(component.listErrorMail).toEqual(["la taille du champs doit être compris entre 6 et 30"]);
  });

  it('should controle password message empty', ()=>{
    component.authentificationForm.patchValue({
      email: "ducloux.y@gmail.com",
      password: "",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="password"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitAuthentification();
    expect(component.listErrorPassword).toEqual(["le champs n'est pas remplit"]);
  });

  it('should controle password message lenght', ()=>{
    component.authentificationForm.patchValue({
      email: "ducloux.y@gmail.com",
      password: "s",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="password"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitAuthentification();
    expect(component.listErrorPassword).toEqual(["la taille du champs doit être compris entre 6 et 30"]);
  });
});
