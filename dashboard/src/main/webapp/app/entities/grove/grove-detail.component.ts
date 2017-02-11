import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Grove } from './grove.model';
import { GroveService } from './grove.service';

@Component({
    selector: 'jhi-grove-detail',
    templateUrl: './grove-detail.component.html'
})
export class GroveDetailComponent implements OnInit, OnDestroy {

    grove: Grove;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private groveService: GroveService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['grove']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.groveService.find(id).subscribe(grove => {
            this.grove = grove;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
