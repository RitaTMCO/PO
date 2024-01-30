package m19;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RulesCollection extends Rule {
    private List<Rule> _rules = new ArrayList<Rule>();

    public void addRule(Rule rule){
        _rules.add(rule);
    }
    @Override
    public int canRequest(User user, Work work){
        int index = 0;
        for(Rule _rule: _rules){
            index = _rule.canRequest(user, work);
            if(index != 0)
                return index;
        }
        return index;
    }
}