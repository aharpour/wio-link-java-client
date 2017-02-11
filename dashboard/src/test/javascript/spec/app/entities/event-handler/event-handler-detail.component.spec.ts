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
import { EventHandlerDetailComponent } from '../../../../../../main/webapp/app/entities/event-handler/event-handler-detail.component';
import { EventHandlerService } from '../../../../../../main/webapp/app/entities/event-handler/event-handler.service';
import { EventHandler } from '../../../../../../main/webapp/app/entities/event-handler/event-handler.model';

describe('Component Tests', () => {

    describe('EventHandler Management Detail Component', () => {
        let comp: EventHandlerDetailComponent;
        let fixture: ComponentFixture<EventHandlerDetailComponent>;
        let service: EventHandlerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [EventHandlerDetailComponent],
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
                    EventHandlerService
                ]
            }).overrideComponent(EventHandlerDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EventHandlerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EventHandlerService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new EventHandler(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.eventHandler).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
