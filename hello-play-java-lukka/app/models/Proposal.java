package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import play.Logger;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.libs.Akka;
import play.libs.F.Function;
import play.libs.F.Function0;
import play.libs.F.Promise;
import scala.concurrent.ExecutionContext;

@Entity
public class Proposal extends Model {

	@Id
	public Long id;
	
	@Required
	public String title;
	
	@Required
	@MinLength(value = 10)
	@MaxLength(value = 1000)
	@Column(length = 1000)
	public String proposal;
	
	@Required
	public SessionType type = SessionType.OneHourTalk;
	
	@Required
	public Boolean isApproved = false;
	
	public String keywords;
	
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public Speaker speaker;

	private static Finder<Long, Proposal> find = new Finder<>(Long.class, Proposal.class);
	
	private static ExecutionContext ctx = Akka.system().dispatchers().lookup("akka.db-dispatcher");
	
	public static Promise<Proposal> findKeynote() {
		return Promise.promise(new Function0<Proposal>() {
			@Override
			public Proposal apply() throws Throwable {
				return find.where().eq("type", SessionType.Keynote).findUnique();
			}
		}, ctx ).recover(new Function<Throwable, Proposal>() {
			@Override
			public Proposal apply(Throwable t) throws Throwable {
				Logger.error("Failed to fetch keynote information", t);
                Proposal s = new Proposal();
                s.title = "COMING SOON!";
                s.proposal = "";
                Speaker speaker = new Speaker();
                speaker.name = "";
                speaker.pictureUrl = "";
                speaker.twitterId = "";
                s.speaker = speaker;
                return s;
			}
		}, ctx);
	}

	public Promise<Void> asyncSave() {
		return Promise.promise(new Function0<Void>() {
			@Override
			public Void apply() throws Throwable {
				save();
				return null;
			}
		}, ctx);
	}
}
