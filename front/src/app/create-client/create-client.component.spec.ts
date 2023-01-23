import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';

import { CreateClientComponent } from './create-client.component';

describe('CreateClientComponent', () => {
  let component: CreateClientComponent;
  let fixture: ComponentFixture<CreateClientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, HttpClientModule],
      declarations: [ CreateClientComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should controle name', ()=>{
    expect(component.controleChamp("yann", 5)).toBeFalse();
  });


  it('should controle mail false', ()=>{
    component.userForm.patchValue({
      email: "ducloux",
      nom: "Ducloux",
      prenom: "Yann",
      password: "password",
    });
    console.log("controle mail false");
    console.log(component.userForm.get('email')?.value);
    fixture.detectChanges();
    console.log(component.controleChampMail()); 
    expect(component.controleChampMail()).toBeFalse();
    component.recupMailMessageError();
    console.log(component.listErrorMail);
  });

  it('should controle mail true', ()=>{
    component.userForm.patchValue({
      email: "ducloux.y@gmail.com",
      nom: "Ducloux",
      prenom: "Yann",
      password: "password",
    });
    console.log("controle mail true");
    console.log(component.userForm.get('email')?.value);
    console.log(component.controleChampMail());
    expect(component.controleChampMail()).toBeTrue();
  });


  it('should controle mail no message error', ()=>{
    component.userForm.patchValue({
      email: "ducloux.y@gmail.com",
      nom: "Ducloux",
      prenom: "Yann",
      password: "password",
    });
    component.recupMailMessageError();
    console.log(component.listErrorMail);
    expect(component.listErrorMail.length).toBe(0);
  });

  it('should controle mail no message', ()=>{
    component.userForm.patchValue({
      email: "duclouxgjgjhgjghghgjhjhjjhjjghgjkghjgk.y@gmail.com",
      nom: "Ducloux",
      prenom: "Yann",
      password: "password",
    });
    console.log("should controle mail no lenght");
    console.log(component.userForm.get('email')?.value);
    console.log(component.userForm.get('email')?.value.length);
    component.recupMailMessageError();
    console.log(component.listErrorMail);
    expect(component.listErrorMail.length).toBe(1);
  });

});
