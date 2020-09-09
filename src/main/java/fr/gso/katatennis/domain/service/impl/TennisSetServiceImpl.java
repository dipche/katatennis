package fr.gso.katatennis.domain.service.impl;

import fr.gso.katatennis.domain.model.TennisSet;
import fr.gso.katatennis.domain.service.TennisSetService;
import fr.gso.katatennis.repository.TennisSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TennisSetServiceImpl implements TennisSetService {

    private final static int MIN_GAMES_TO_WIN_SET = 6;
    private final static int GAMES_GAP_TO_WIN_SET = 2;
    private final static int GAMES_MAX_AT_TIE_BREAK = 7;
    private final static int GAMES_MIN_AT_TIE_BREAK = 6;

    @Autowired
    private TennisSetRepository tennisSetRepository;

    public Integer computeMatchNextSetNumber(Integer matchId) {
        List<TennisSet> sets = tennisSetRepository.findAllByMatchId(matchId);
        return sets.size() + 1;
    }

    public TennisSet updateASet(TennisSet tennisSetToUpdate) {
        return tennisSetRepository.save(tennisSetToUpdate);
    }

    public boolean isSetFinished(TennisSet aTennisSet) {
        return ((aTennisSet.getPlayer1SetScore() == MIN_GAMES_TO_WIN_SET || aTennisSet.getPlayer2SetScore() == MIN_GAMES_TO_WIN_SET)
                && Math.abs(aTennisSet.getPlayer1SetScore() - aTennisSet.getPlayer2SetScore()) >= GAMES_GAP_TO_WIN_SET)
                || ((aTennisSet.getPlayer1SetScore() == GAMES_MAX_AT_TIE_BREAK && aTennisSet.getPlayer2SetScore() == GAMES_MIN_AT_TIE_BREAK)
                || (aTennisSet.getPlayer1SetScore() == GAMES_MIN_AT_TIE_BREAK && aTennisSet.getPlayer2SetScore() == GAMES_MAX_AT_TIE_BREAK));
    }
}
