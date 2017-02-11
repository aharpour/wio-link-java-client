import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TaskHandlerDetailComponent } from '../../../../../../main/webapp/app/entities/task-handler/task-handler-detail.component';
import { TaskHandlerService } from '../../../../../../main/webapp/app/entities/task-handler/task-handler.service';
import { TaskHandler } from '../../../../../../main/webapp/app/entities/task-handler/task-handler.model';

describe('Component Tests', () => {

    describe('TaskHandler Management Detail Component', () => {
        let comp: TaskHandlerDetailComponent;
        let fixture: ComponentFixture<TaskHandlerDetailComponent>;
        let service: TaskHandlerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [TaskHandlerDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    TaskHandlerService
                ]
            }).overrideComponent(TaskHandlerDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TaskHandlerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaskHandlerService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new TaskHandler(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.taskHandler).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
