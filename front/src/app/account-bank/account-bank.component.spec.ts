import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
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
    storageService.saveData('token', "test_token");
    component.ngOnInit();
    component.toHome();
    expect(navigateSpy).toHaveBeenCalledWith(['/welcome']);
  }));

});
