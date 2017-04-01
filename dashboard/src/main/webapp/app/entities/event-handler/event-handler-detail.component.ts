import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService, DataUtils } from 'ng-jhipster';
import { EventHandler } from './event-handler.model';
import { EventHandlerService } from './event-handler.service';

@Component({
    selector: 'jhi-event-handler-detail',
    templateUrl: './event-handler-detail.component.html'
})
export class EventHandlerDetailComponent implements OnInit, OnDestroy {

    eventHandler: EventHandler;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private eventHandlerService: EventHandlerService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['eventHandler', 'langauge']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.eventHandlerService.find(id).subscribe(eventHandler => {
            this.eventHandler = eventHandler;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
