import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { CreateClientComponent } from './create-client.component';

describe('CreateClientComponent', () => {
  let component: CreateClientComponent;
  let fixture: ComponentFixture<CreateClientComponent>;
  let router: Router;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, HttpClientModule, RouterTestingModule.withRoutes([])],
      declarations: [ CreateClientComponent ],
    providers: [FormBuilder],
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should controle name', ()=>{
    expect(component.controleChamp("yann", 5)).toBeFalse();
  });


  it('should controle mail false', ()=>{
    fixture.detectChanges();
    component.userForm.setValue({
      email: "duclouxmail",
      nom: "Ducloux",
      prenom: "Yann",
      password: "password",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="email"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    expect(component.controleChampMail()).toBeFalse();
  });

  it('should controle mail true', ()=>{
    component.userForm.setValue({
      email: "ducloux.y@gmail.com",
      nom: "Ducloux",
      prenom: "Yann",
      password: "password",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="email"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    expect(component.controleChampMail()).toBeTrue();
  });


  it('should controle mail no message error', ()=>{
    component.userForm.patchValue({
      email: "ducloux.y@gmail.com",
      nom: "Ducloux",
      prenom: "Yann",
      password: "password",
    });
    let listError = component.recupMailMessageError();
    expect(listError.length).toBe(0);
  });

  it('should controle mail message empty', ()=>{
    component.userForm.patchValue({
      email: "",
      nom: "Ducloux",
      prenom: "Yann",
      password: "password",
    });
    let listError:String[] = component.recupMailMessageError();
    expect(listError.length).toBe(1);
    expect(listError[0]).toBe("le champs n'est pas remplit");
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
    let listError:String[] = component.recupMailMessageError();
    expect(listError.length).toBe(1);
    expect(listError[0]).toBe("la taille du champs doit être compris entre 6 et 30");
  });


  it('should controle mail message pattern', ()=>{
    component.userForm.patchValue({
      email: "ducloux",
      nom: "Ducloux",
      prenom: "Yann",
      password: "password",
    });
    const inputElement = fixture.debugElement.query(By.css('input[name="email"]')).nativeElement;
    inputElement.dispatchEvent(new Event('input'));
    let listError:String[] = component.recupMailMessageError();
    expect(listError.length).toBe(1);
    expect(listError[0]).toBe("Erreur pattern mail");
  });


  it('should controle navigate home', waitForAsync(()=>{  
    const component = fixture.componentInstance;
    const navigateSpy = spyOn(router, 'navigate');
    component.toHome();
    expect(navigateSpy).toHaveBeenCalledWith(['/']);
  }));
});
