import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePollDialogComponent } from './create-poll-dialog.component';

describe('CreatePollDialogComponent', () => {
  let component: CreatePollDialogComponent;
  let fixture: ComponentFixture<CreatePollDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreatePollDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreatePollDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
