package ru.skyPro.recommendationServiceBank.service;

import org.springframework.stereotype.Service;
import ru.skyPro.recommendationServiceBank.exceptions.RecommendBankException;
import ru.skyPro.recommendationServiceBank.model.BankRecommendation;
import ru.skyPro.recommendationServiceBank.model.ClientRecommendation;
import ru.skyPro.recommendationServiceBank.repository.RecommendationsRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecommendationsService {
    private final RecommendationsRepository repository;
    private final String DESCRIPTION_ONE = "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!";
    private final String DESCRIPTION_TWO = "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем! Преимущества «Копилки»: Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет. Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости. Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!";
    private final String DESCRIPTION_THREE = "Простой кредит. Откройте мир выгодных кредитов с нами!Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.Почему выбирают нас: Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов. Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении. Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое. Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!";

    private final String RECOMMENDATION_ONE = "Invest500";
    private final String RECOMMENDATION_TWO = "Top Saving";
    private final String RECOMMENDATION_THREE = "Simple Credit";

    public RecommendationsService(RecommendationsRepository repository) {
        this.repository = repository;
    }

    public ClientRecommendation getClientRecommendation(UUID user) {
        ClientRecommendation client;
        List<String> listProducts = repository.getRecommenationForUser(user);
        List<BankRecommendation> bankServiceRecommendations = new ArrayList<>();

        if (listProducts.contains(RECOMMENDATION_ONE)) {
            bankServiceRecommendations.add(new BankRecommendation(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"), RECOMMENDATION_ONE, DESCRIPTION_ONE));
            client = new ClientRecommendation(user, bankServiceRecommendations);
            return client;
        } else if (listProducts.contains(RECOMMENDATION_TWO)) {
            bankServiceRecommendations.add(new BankRecommendation(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"), RECOMMENDATION_TWO, DESCRIPTION_TWO));
            client = new ClientRecommendation(user, bankServiceRecommendations);
            return client;
        } else if (listProducts.contains(RECOMMENDATION_THREE)) {
            bankServiceRecommendations.add(new BankRecommendation(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), RECOMMENDATION_THREE, DESCRIPTION_THREE));
            client = new ClientRecommendation(user, bankServiceRecommendations);
            return client;
        } else {
            throw new RecommendBankException();
        }
    }
}

