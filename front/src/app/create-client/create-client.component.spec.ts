import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Observable, of, throwError } from 'rxjs';
import { ClientDTO } from 'src/interface/clientDTO';
import { ApiService } from '../services/api.service';
import { CreateClientComponent } from './create-client.component';

describe('CreateClientComponent', () => {
  let component: CreateClientComponent;
  let fixture: ComponentFixture<CreateClientComponent>;
  let router: Router;
  let apiService: ApiService;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, HttpClientModule],
      declarations: [ CreateClientComponent ],
      providers: [FormBuilder],
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateClientComponent);
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
  
  it('should controle create client Valid', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.userForm.patchValue({
      email: "ducloux.y@gmail.com",
      nom: "Ducloux",
      prenom: "Yann",
      password: "password",
    });
    const navigateSpy = spyOn(router, 'navigate');
    let observableClientDTO : Observable<ClientDTO> = of(new ClientDTO("ducloux.y@gmail.com", "Ducloux", "Yann"));
    spyOn(apiService, "createClient").and.returnValue(observableClientDTO);
    component.onFormSubmitUser();
    expect(navigateSpy).toHaveBeenCalledWith(['/authentification']);
  }));

  
  it('should controle create client InValid mail exist', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.userForm.patchValue({
      email: "ducloux.y@gmail.com",
      nom: "Ducloux",
      prenom: "Yann",
      password: "password",
    });
    let messageError = "le mail existe dèjà";
    const errorResponse = new HttpErrorResponse({
      error: messageError,
      status: 400,
      statusText: 'OK',
   });
    spyOn(apiService, "createClient").and.returnValue(throwError(() => errorResponse));
    spyOn(window, "alert");
    component.onFormSubmitUser();
    expect(window.alert).toHaveBeenCalledWith(messageError);
  }));
  
  it('should controle mail meesage pattern', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    component.userForm.patchValue({
      email: "ducloux",
      nom: "Ducloux",
      prenom: "Yann",
      password: "password",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="email"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitUser();
    expect(component.listErrorMail).toEqual(["Erreur pattern mail"]);
  }));

  it('should controle mail message empty', ()=>{
    component.userForm.patchValue({
      email: "",
      nom: "Ducloux",
      prenom: "Yann",
      password: "password",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="email"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitUser();
    expect(component.listErrorMail).toEqual(["le champs n'est pas remplit"]);
  });

  it('should controle mail message lenght', ()=>{
    component.userForm.patchValue({
      email: "fdkjkjfdlkjdslkjflkjfdsk@gmail.com",
      nom: "Ducloux",
      prenom: "Yann",
      password: "password",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="email"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitUser();
    expect(component.listErrorMail).toEqual(["la taille du champs doit être compris entre 6 et 30"]);
  });

  it('should controle nom message empty', ()=>{
    component.userForm.patchValue({
      email: "ducloux.y@gmail.com",
      nom: "",
      prenom: "Yann",
      password: "password",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="nom"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitUser();
    expect(component.listErrorNom).toEqual(["le champs n'est pas remplit"]);
  });

  it('should controle nom message lenght', ()=>{
    component.userForm.patchValue({
      email: "ducloux.y@gmail.com",
      nom: "x",
      prenom: "Yann",
      password: "password",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="nom"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitUser();
    expect(component.listErrorNom).toEqual(["la taille du champs doit être compris entre 2 et 30"]);
  });

  it('should controle prénom message empty', ()=>{
    component.userForm.patchValue({
      email: "ducloux.y@gmail.com",
      nom: "ducloux",
      prenom: "",
      password: "password",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="prenom"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitUser();
    expect(component.listErrorPrenom).toEqual(["le champs n'est pas remplit"]);
  });

  it('should controle prénom message lenght', ()=>{
    component.userForm.patchValue({
      email: "ducloux.y@gmail.com",
      nom: "ducloux",
      prenom: "y",
      password: "password",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="prenom"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitUser();
    expect(component.listErrorPrenom).toEqual(["la taille du champs doit être compris entre 2 et 30"]);
  });

  it('should controle password message empty', ()=>{
    component.userForm.patchValue({
      email: "ducloux.y@gmail.com",
      nom: "ducloux",
      prenom: "Yann",
      password: "",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="password"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitUser();
    expect(component.listErrorPassword).toEqual(["le champs n'est pas remplit"]);
  });

  it('should controle password message lenght', ()=>{
    component.userForm.patchValue({
      email: "ducloux.y@gmail.com",
      nom: "ducloux",
      prenom: "Yann",
      password: "passwdsflksjdfsdfsqfsdfsdfsqdfsdqfdsf",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="password"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    component.onFormSubmitUser();
    expect(component.listErrorPassword).toEqual(["la taille du champs doit être compris entre 6 et 30"]);
  });
});
