package controllers;

import models.Proposal;
import play.data.Form;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
    
	private static final Form<Proposal> proposalForm = Form.form(Proposal.class);
	
    public static Promise<Result> index() {
    	Promise<Proposal> keynote = Proposal.findKeynote();
		Promise<Result> result = keynote.map(new Function<Proposal, Result>() {
			@Override
			public Result apply(Proposal keynote) throws Throwable {
				return ok(views.html.index.render(keynote));
			}
		});
    	return result;
    }

    public static Result newProposal() {
    	return ok(views.html.newProposal.render(proposalForm));
    }
    
    public static Promise<Result> submitProposal() {
    	Form<Proposal> submittedFrom = proposalForm.bindFromRequest();
    	if (submittedFrom.hasErrors()) {
    		return Promise.<Result>pure(ok(views.html.newProposal.render(submittedFrom)));
    	} else {
    		Proposal proposal = submittedFrom.get();
    		Promise<Result> result = proposal.asyncSave().map(new Function<Void, Result>() {
    			@Override
    			public Result apply(Void arg0) throws Throwable {
    	    		flash("message", "Thanks for submitting a proposal");
    	    		return redirect(routes.Application.index());
    			}
    		});
    		return result;
    	}
    }
}
