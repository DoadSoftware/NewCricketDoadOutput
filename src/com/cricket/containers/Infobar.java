package com.cricket.containers;

import java.util.List;

import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;

public class Infobar {
	
	private boolean infobar_on_screen = false;
	private boolean bottom_infobar_on_screen = false;
	private boolean powerplay_on_screen = false;
	private boolean powerplay_end = false;
	private boolean infobar_pushed = false;
	private boolean infobar_shrink = false;
	private boolean thisOvers_Title_Fade = false;
	private boolean challengeRunOnScreen = false;
	private boolean result_on_screen = false;
	private boolean top_stage = false;
	private boolean right_section_play = false;
	private boolean right_bottom_play = false;
	private boolean player_impact = false;
	private boolean target_on_screen = false;
	private boolean target_pushed = false;
	private boolean FieldPlotter_on_screen = false;
	private boolean is_player_outOrnot = false;
	private boolean player_outOrnot = false;
	private boolean freeHit_on_screen = false;
	private boolean forced_powerplay_out = false;
	private boolean superOverThisOverOnScreen = false;
	
	private int player_id,omo_value,omo_value_bat;
	
	private String infobar_status,infobar_photo,infobat_center_data,infobar_photo_bat;
	private String BatsmanAndBowlOrSponsor;
	
	private String Left_bottom;
	private String Last_left_bottom;
	
	private String Right_top;
	private String Last_right_top;
	
	private String Right_bottom;
	private String Last_right_bottom;

	private String Middle_section;
	private String Last_middle_section;
	
	private String Right_section;
	private String Last_right_section;
	
	private String full_section;
	private String last_full_section;
	
	private String full_promo_section;
	private String last_full_promo_section;
	
	private String right_full_section;
	private String last_right_full_section;
	
	private String infobar_ident_section;
	
	private List<BattingCard> last_batsmen;
	private BowlingCard last_bowler;
	private String last_this_over;
	
	private String last_speed_value;
	private String last_ball_value;
	private String last_wide_value;
	private String last_noball_value;
	
	private List<String> freeText;
	
	public String getFull_section() {
		return full_section;
	}
	public void setFull_section(String full_section) {
		this.full_section = full_section;
	}
	public String getLast_full_section() {
		return last_full_section;
	}
	public void setLast_full_section(String last_full_section) {
		this.last_full_section = last_full_section;
	}
	public String getInfobar_ident_section() {
		return infobar_ident_section;
	}
	public void setInfobar_ident_section(String infobar_ident_section) {
		this.infobar_ident_section = infobar_ident_section;
	}
	public boolean isChallengeRunOnScreen() {
		return challengeRunOnScreen;
	}
	public void setChallengeRunOnScreen(boolean challengeRunOnScreen) {
		this.challengeRunOnScreen = challengeRunOnScreen;
	}
	public List<String> getFreeText() {
		return freeText;
	}
	public void setFreeText(List<String> freeText) {
		this.freeText = freeText;
	}
	public boolean isInfobar_pushed() {
		return infobar_pushed;
	}
	public void setInfobar_pushed(boolean infobar_pushed) {
		this.infobar_pushed = infobar_pushed;
	}
	public String getInfobar_status() {
		return infobar_status;
	}
	public void setInfobar_status(String infobar_status) {
		this.infobar_status = infobar_status;
	}
	public String getLast_this_over() {
		return last_this_over;
	}
	public void setLast_this_over(String last_this_over) {
		this.last_this_over = last_this_over;
	}
	public boolean isInfobar_on_screen() {
		return infobar_on_screen;
	}
	public void setInfobar_on_screen(boolean infobar_on_screen) {
		this.infobar_on_screen = infobar_on_screen;
	}
	public boolean isPowerplay_on_screen() {
		return powerplay_on_screen;
	}
	public void setPowerplay_on_screen(boolean powerplay_on_screen) {
		this.powerplay_on_screen = powerplay_on_screen;
	}
	public List<BattingCard> getLast_batsmen() {
		return last_batsmen;
	}
	public void setLast_batsmen(List<BattingCard> last_batsmen) {
		this.last_batsmen = last_batsmen;
	}
	public BowlingCard getLast_bowler() {
		return last_bowler;
	}
	public void setLast_bowler(BowlingCard last_bowler) {
		this.last_bowler = last_bowler;
	}
	public String getLeft_bottom() {
		return Left_bottom;
	}
	public void setLeft_bottom(String left_bottom) {
		Left_bottom = left_bottom;
	}
	public String getLast_left_bottom() {
		return Last_left_bottom;
	}
	public void setLast_left_bottom(String last_left_bottom) {
		Last_left_bottom = last_left_bottom;
	}
	public String getRight_bottom() {
		return Right_bottom;
	}
	public void setRight_bottom(String right_bottom) {
		Right_bottom = right_bottom;
	}
	public String getLast_right_bottom() {
		return Last_right_bottom;
	}
	public void setLast_right_bottom(String last_right_bottom) {
		Last_right_bottom = last_right_bottom;
	}
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public String getMiddle_section() {
		return Middle_section;
	}
	public void setMiddle_section(String middle_section) {
		Middle_section = middle_section;
	}
	public String getLast_middle_section() {
		return Last_middle_section;
	}
	public void setLast_middle_section(String last_middle_section) {
		Last_middle_section = last_middle_section;
	}
	public String getBatsmanAndBowlOrSponsor() {
		return BatsmanAndBowlOrSponsor;
	}
	public void setBatsmanAndBowlOrSponsor(String batsmanAndBowlOrSponsor) {
		BatsmanAndBowlOrSponsor = batsmanAndBowlOrSponsor;
	}
	public String getRight_section() {
		return Right_section;
	}
	public void setRight_section(String right_section) {
		Right_section = right_section;
	}
	public String getLast_right_section() {
		return Last_right_section;
	}
	public void setLast_right_section(String last_right_section) {
		Last_right_section = last_right_section;
	}
	public String getRight_top() {
		return Right_top;
	}
	public void setRight_top(String right_top) {
		Right_top = right_top;
	}
	public String getLast_right_top() {
		return Last_right_top;
	}
	public void setLast_right_top(String last_right_top) {
		Last_right_top = last_right_top;
	}
	public boolean isThisOvers_Title_Fade() {
		return thisOvers_Title_Fade;
	}
	public void setThisOvers_Title_Fade(boolean thisOvers_Title_Fade) {
		this.thisOvers_Title_Fade = thisOvers_Title_Fade;
	}
	public boolean isPowerplay_end() {
		return powerplay_end;
	}
	public void setPowerplay_end(boolean powerplay_end) {
		this.powerplay_end = powerplay_end;
	}
	public boolean isResult_on_screen() {
		return result_on_screen;
	}
	public void setResult_on_screen(boolean result_on_screen) {
		this.result_on_screen = result_on_screen;
	}
	public boolean isTop_stage() {
		return top_stage;
	}
	public void setTop_stage(boolean top_stage) {
		this.top_stage = top_stage;
	}
	public boolean isRight_section_play() {
		return right_section_play;
	}
	public void setRight_section_play(boolean right_section_play) {
		this.right_section_play = right_section_play;
	}
	public boolean isPlayer_impact() {
		return player_impact;
	}
	public void setPlayer_impact(boolean player_impact) {
		this.player_impact = player_impact;
	}
	public boolean isTarget_on_screen() {
		return target_on_screen;
	}
	public void setTarget_on_screen(boolean target_on_screen) {
		this.target_on_screen = target_on_screen;
	}
	public boolean isFieldPlotter_on_screen() {
		return FieldPlotter_on_screen;
	}
	public void setFieldPlotter_on_screen(boolean fieldPlotter_on_screen) {
		FieldPlotter_on_screen = fieldPlotter_on_screen;
	}
	public boolean isIs_player_outOrnot() {
		return is_player_outOrnot;
	}
	public void setIs_player_outOrnot(boolean is_player_outOrnot) {
		this.is_player_outOrnot = is_player_outOrnot;
	}
	public boolean isPlayer_outOrnot() {
		return player_outOrnot;
	}
	public void setPlayer_outOrnot(boolean player_outOrnot) {
		this.player_outOrnot = player_outOrnot;
	}
	public String getLast_speed_value() {
		return last_speed_value;
	}
	public void setLast_speed_value(String last_speed_value) {
		this.last_speed_value = last_speed_value;
	}
	public String getLast_ball_value() {
		return last_ball_value;
	}
	public void setLast_ball_value(String last_ball_value) {
		this.last_ball_value = last_ball_value;
	}
	public String getLast_wide_value() {
		return last_wide_value;
	}
	public void setLast_wide_value(String last_wide_value) {
		this.last_wide_value = last_wide_value;
	}
	public String getLast_noball_value() {
		return last_noball_value;
	}
	public void setLast_noball_value(String last_noball_value) {
		this.last_noball_value = last_noball_value;
	}
	public boolean isTarget_pushed() {
		return target_pushed;
	}
	public void setTarget_pushed(boolean target_pushed) {
		this.target_pushed = target_pushed;
	}
	public boolean isBottom_infobar_on_screen() {
		return bottom_infobar_on_screen;
	}
	public void setBottom_infobar_on_screen(boolean bottom_infobar_on_screen) {
		this.bottom_infobar_on_screen = bottom_infobar_on_screen;
	}
	public boolean isFreeHit_on_screen() {
		return freeHit_on_screen;
	}
	public void setFreeHit_on_screen(boolean freeHit_on_screen) {
		this.freeHit_on_screen = freeHit_on_screen;
	}
	public boolean isRight_bottom_play() {
		return right_bottom_play;
	}
	public void setRight_bottom_play(boolean right_bottom_play) {
		this.right_bottom_play = right_bottom_play;
	}
	public boolean isInfobar_shrink() {
		return infobar_shrink;
	}
	public void setInfobar_shrink(boolean infobar_shrink) {
		this.infobar_shrink = infobar_shrink;
	}
	public String getInfobar_photo() {
		return infobar_photo;
	}
	public void setInfobar_photo(String infobar_photo) {
		this.infobar_photo = infobar_photo;
	}
	public int getOmo_value() {
		return omo_value;
	}
	public void setOmo_value(int omo_value) {
		this.omo_value = omo_value;
	}
	public String getInfobat_center_data() {
		return infobat_center_data;
	}
	public void setInfobat_center_data(String infobat_center_data) {
		this.infobat_center_data = infobat_center_data;
	}
	public boolean isForced_powerplay_out() {
		return forced_powerplay_out;
	}
	public void setForced_powerplay_out(boolean forced_powerplay_out) {
		this.forced_powerplay_out = forced_powerplay_out;
	}
	public int getOmo_value_bat() {
		return omo_value_bat;
	}
	public void setOmo_value_bat(int omo_value_bat) {
		this.omo_value_bat = omo_value_bat;
	}
	public String getInfobar_photo_bat() {
		return infobar_photo_bat;
	}
	public void setInfobar_photo_bat(String infobar_photo_bat) {
		this.infobar_photo_bat = infobar_photo_bat;
	}
	public boolean isSuperOverThisOverOnScreen() {
		return superOverThisOverOnScreen;
	}
	public void setSuperOverThisOverOnScreen(boolean superOverThisOverOnScreen) {
		this.superOverThisOverOnScreen = superOverThisOverOnScreen;
	}
	public String getRight_full_section() {
		return right_full_section;
	}
	public void setRight_full_section(String right_full_section) {
		this.right_full_section = right_full_section;
	}
	public String getLast_right_full_section() {
		return last_right_full_section;
	}
	public void setLast_right_full_section(String last_right_full_section) {
		this.last_right_full_section = last_right_full_section;
	}
	public String getFull_promo_section() {
		return full_promo_section;
	}
	public void setFull_promo_section(String full_promo_section) {
		this.full_promo_section = full_promo_section;
	}
	public String getLast_full_promo_section() {
		return last_full_promo_section;
	}
	public void setLast_full_promo_section(String last_full_promo_section) {
		this.last_full_promo_section = last_full_promo_section;
	}
	
}
