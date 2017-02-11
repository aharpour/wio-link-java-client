import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Node } from './node.model';
import { NodeService } from './node.service';

@Component({
    selector: 'jhi-node-detail',
    templateUrl: './node-detail.component.html'
})
export class NodeDetailComponent implements OnInit, OnDestroy {

    node: Node;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private nodeService: NodeService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['node']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.nodeService.find(id).subscribe(node => {
            this.node = node;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
