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
import { NodeDetailComponent } from '../../../../../../main/webapp/app/entities/node/node-detail.component';
import { NodeService } from '../../../../../../main/webapp/app/entities/node/node.service';
import { Node } from '../../../../../../main/webapp/app/entities/node/node.model';

describe('Component Tests', () => {

    describe('JpaNodeBean Management Detail Component', () => {
        let comp: NodeDetailComponent;
        let fixture: ComponentFixture<NodeDetailComponent>;
        let service: NodeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [NodeDetailComponent],
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
                    NodeService
                ]
            }).overrideComponent(NodeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Node(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.node).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
