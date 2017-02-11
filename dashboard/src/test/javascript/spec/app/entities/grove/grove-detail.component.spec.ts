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
import { GroveDetailComponent } from '../../../../../../main/webapp/app/entities/grove/grove-detail.component';
import { GroveService } from '../../../../../../main/webapp/app/entities/grove/grove.service';
import { Grove } from '../../../../../../main/webapp/app/entities/grove/grove.model';

describe('Component Tests', () => {

    describe('JpaGroveBean Management Detail Component', () => {
        let comp: GroveDetailComponent;
        let fixture: ComponentFixture<GroveDetailComponent>;
        let service: GroveService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [GroveDetailComponent],
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
                    GroveService
                ]
            }).overrideComponent(GroveDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GroveDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroveService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Grove(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.grove).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
