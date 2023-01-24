import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatOptionModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { BrowserModule, By } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from '../app-routing.module';

import { CreateClientComponent } from './create-client.component';

describe('CreateClientComponent', () => {
  let component: CreateClientComponent;
  let fixture: ComponentFixture<CreateClientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, HttpClientModule,
      
      
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatTableModule,
    MatIconModule,
    MatOptionModule,
    MatSelectModule,],
      declarations: [ CreateClientComponent ],
      providers: [FormBuilder],
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
    let listError = component.recupMailMessageError();
    expect(listError.length).toBe(1);
  });

});
