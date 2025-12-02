package com.app.demo;

import android.app.Application;
import com.app.utils.AppDir;
import com.app.utils.FrescoUtil;
import org.litepal.LitePal;

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (instance != null) {
            LitePal.initialize(instance);
            AppDir.getInstance(this);
            FrescoUtil.init(instance);
            // 移除 initMap() 調用
        }
    }

    public static MyApplication getInstance() {
        return instance;
    }

    // 數據按照 labels.txt 順序重新排列
    // 0: chapel
    // 1: gold_seashore
    // 2: hobe_mackay_hospital
    // 3: hongmaocheng
    // 4: lovers_bridge
    // 5: mackay_landing
    // 6: mackay_statue
    // 7: oxford_college
    // 8: yunmen_cloud

    // 圖片
    public static String[] picUrls = {
            "https://i.imgur.com/tfyBORX.jpeg", // chapel
            "https://i.imgur.com/FxNXfxF.jpeg", // gold_seashore
            "https://i.imgur.com/MTio6Wc.jpeg", // hobe_mackay_hospital
            "https://i.imgur.com/iLYqur2.jpeg", // hongmaocheng
            "https://i.imgur.com/vUtgJTq.jpeg", // lovers_bridge
            "https://i.imgur.com/qUvp8YC.jpeg", // mackay_landing
            "https://i.imgur.com/Jcjoaru.jpeg", // mackay_statue
            "https://i.imgur.com/hkEb1Zq.jpeg", // oxford_college
            "https://i.imgur.com/PmSCHIp.jpeg", // yunmen_cloud
    };

    // 名稱
    public static String[] names = {
            "大禮拜堂 ", // chapel
            "金色水岸 ", // gold_seashore
            "滬尾偕醫館 ", // hobe_mackay_hospital
            "淡水紅毛城", // hongmaocheng
            "淡水漁人碼頭", // lovers_bridge
            "馬偕上岸處 ", // mackay_landing
            "馬偕博士雕像 ", // mackay_statue
            "真理理學堂大書院 ", // oxford_college
            "淡水雲門舞集 ", // yunmen_cloud
    };

    // 英文名稱
    public static String[] namesEn = {
            "Great Chapel", // chapel
            "Golden Waterfront", // gold_seashore
            "Hobe Mackay Clinic", // hobe_mackay_hospital
            "Fort San Domingo", // hongmaocheng
            "Tamsui Fisherman's Wharf", // lovers_bridge
            "Mackay's Landing Site", // mackay_landing
            "Dr. Mackay Statue", // mackay_statue
            "Tamsui Oxford College", // oxford_college
            "Cloud Gate Culture", // yunmen_cloud
    };

    // 日文名稱
    public static String[] namesJa = {
            "大礼拝堂", // chapel
            "ゴールデン・ウォーターフロント", // gold_seashore
            "ホベ・マッカイ病院", // hobe_mackay_hospital
            "フォート・サン・ドミンゴ", // hongmaocheng
            "タムシウ漁港", // lovers_bridge
            "マッカイ上陸地点", // mackay_landing
            "マッカイ博士像", // mackay_statue
            "真理学堂大书院", // oxford_college
            "クラウドゲートダンスシアター", // yunmen_cloud
    };

    // 韓文名稱
    public static String[] namesKo = {
            "대예배당", // chapel
            "골든 워터프론트", // gold_seashore
            "호베 막사이 병원", // hobe_mackay_hospital
            "포트 산도미ngo", // hongmaocheng
            "탄수어부항", // lovers_bridge
            "막사이 상륙지점", // mackay_landing
            "막사이 박사 동상", // mackay_statue
            "진리학당 대서원", // oxford_college
            "클라우드 게이트 댄스 극장", // yunmen_cloud
    };

    // 簡介
    public static String[] conts = {
            "獨特尖拱造型建於1882年的大禮拜堂樓層分地下三層、地上四層、屋突三層，合計十層。 建築方面為了突顯基督教教義風格，重複使用象徵「虛心祈禱的手」的尖拱造型，無論在主體建築、窗戶和大門，皆塑造出建築的獨特風格。\n建造於斜坡上，氣勢恢宏，現在已成為婚攝熱門地的大禮拜堂。", // chapel
            "大家所熟悉的淡水河岸，其實有條名聲很優雅的自行車道，叫做「金色水岸」，起點自關渡宮附近的中港河入口一直沿著河岸到淡水老街。 這條自行車道長約10公里，非常平整好騎，很適合親子一起觀察白鷺鷥與招潮蟹等生態，一邊享受騎乘樂趣。騎到鼎鼎大名的「淡水老街」，在街道兩旁林立熱鬧的商店，包含有濃濃古早味的米行、餅舖、布店、雜貨店等；以及充滿現代感的潮流服飾、玩具等，形成古今交融的特殊商圈文化。老街裡可以尋寶、品嚐美食，還有古蹟可參觀，怎麼玩都不會膩，是遊人必去的超人氣景點。老街分成外側靠淡水河岸的部分(金色水岸步道)與內側的老街，著名在地特產包括古早味現烤蛋糕、阿給、烤魷魚、阿婆鐵蛋、魚酥、巨無霸冰淇淋、魚丸等等，都是來老街非吃不可的美食。", // gold_seashore
            "滬尾偕醫館，是新北市古蹟之一，也是臺灣第一家西式醫院，成立於1879年9月14日，由馬偕醫生主持，此處也曾是他的住所\n", // hobe_mackay_hospital
            "紅毛城（閩南語：Âng-mn ̂ g-siânn），舊稱安東尼堡，是一座位於新北市淡水區的古蹟。 最早建城是在1628年統治北部的西班牙人所興建的“聖多明哥城”，但後來聖多明哥城遭到摧毀，1644年荷蘭人於聖多明哥城原址附近予以重建，並命名為“安東尼堡”。 而由於當時稱呼荷蘭人為紅毛，囙此這個城就被他們稱作紅毛城。\n 1724年，臺灣開始整修紅毛城，增辟了四座週邊城門。1867年以後，紅毛城開始由英國政府租用，作為領事館，並於其旁興建領事官邸。太平洋戰爭期間，日本向英美宣戰，並曾短暫查封紅毛城，但於戰後即被交還與英方。爾後，依序由澳大利亞與美國代為管理。一直到1980年，該城被指定為一級古蹟並開放供群眾參觀。紅毛城被視為臺灣現存最古老的建築之一。 ", // hongmaocheng
            "漁人碼頭位於淡水河出海口右岸，舊名淡水第二漁港，以夕陽餘暉著稱。除了賞夕陽之外，必走招牌地標情人橋、搭渡輪走藍色公路或沿著原木棧道賞漁港風景，亦可登上情人塔將淡水景緻一覽無遺。； 行政院農業委員會為改善漁港工作與漁村生活環境，自1998年起選定此處為多功能示範漁港，大力補助經費，並且將於漁人碼頭規劃成為一個兼具漁業發展與觀光休閒的優質公園，成功的朝漁港功能多元化方向發展，漁人碼頭經過整地興建，成為重要的觀光活動休閒的場地，漁人碼頭邊停滿了許多漁船及遊輪，更常成為廣告片拍攝的場景。\n", // lovers_bridge
            "位於淡水郵局後方河岸處，由國立臺北藝術大學陳愷璜先生所作，銅像設計為馬偕上岸的意象，又稱「馬偕藝術銅像」；馬偕博士手捧聖經跪在地面上禱告，位於他身旁的是他搭乘而來的小船，因此，此景點全稱為「馬偕上岸小方舟及馬偕禱告雕像」。1872年3月9日下午3時，加拿大宣教士馬偕博士搭「海龍號」由此登陸，自此以淡水為其宣教、醫療和教育之基地，並擇淡水為家，在此娶妻生子，死後埋骨於斯土。", // mackay_landing
            "喬治·萊斯里·馬偕（George Leslie Mackay，1844年3月21日-1901年6月2日）生於加拿大安大略省，加拿大長老會差會牧師，雖然不是醫師但學過解剖學與生理學課程。將生命奉獻給臺灣的牧師。漢名叫偕睿理，但一般稱「馬偕博士」。於19世紀末期至台灣傳教與行醫，與馬雅各齊名。馬偕的一生被以「寧願燒盡，不願鏽壞」讚賞。", // mackay_statue
            "理學堂大書院是全臺灣第一座西式學堂，於1882年由馬偕博士於故鄉加拿大牛津郡募款創立，位於真理大學內，又名牛津學堂，1985年經內政部指定為二級古蹟，如今成為真理大學校史館，陳展學校與馬偕相關文史資料。 ", // oxford_college
            "「為所有人起舞」是雲門創團的宗旨。除了正式劇場的公演，舞團每年在台灣不同城市舉辦大型戶外演出。每場觀眾數萬，演出時秩序井然，現場不留一片紙屑，西方媒體譽為「地表上最大的舞蹈演出」 ", // yunmen_cloud
    };

    // 英文簡介
    public static String[] contsEn = {
            "The unique pointed-arch style chapel, built in 1882, consists of three underground floors, four above-ground floors, and three rooftop floors, totaling ten levels. To highlight Christian teachings, the design repeatedly uses the pointed arch, symbolizing \"hands in humble prayer,\" in the main structure, windows, and doors, creating a distinctive architectural style.\n" +
                    "Built on a slope, the grand chapel has become a popular spot for wedding photography.", // chapel
            "The familiar Tamsui riverside is home to a beautifully named bike path called the \"Golden Waterfront.\" It stretches about 10 kilometers from the Zhonggang River entrance near Guandu Temple to Tamsui Old Street. This flat and easy-to-ride path is perfect for families to observe egrets and fiddler crabs while enjoying the ride. Arriving at the famous Tamsui Old Street, visitors can explore bustling shops offering traditional rice shops, pastry stores, cloth shops, and general stores, alongside modern fashion and toy stores, creating a unique blend of old and new. The street is divided into the outer riverside section (Golden Waterfront Walkway) and the inner old street. Famous local specialties include freshly baked cakes, agei (tofu stuffed with noodles), grilled squid, Grandma's Iron Eggs, fish crisps, giant ice cream, and fish balls, all must-try delicacies.", // gold_seashore
            "Hobe Mackay Hospital is one of the historic sites in New Taipei City and the first Western-style hospital in Taiwan. Established on September 14, 1879, it was run by Dr. Mackay and also served as his residence.", // hobe_mackay_hospital
            "Fort San Domingo (Hokkien: Âng-mn̂g-siânn), formerly known as Fort Antonio, is a historic site in Tamsui District, New Taipei City. Originally built in 1628 by the Spanish who ruled northern Taiwan as \"Fort Santo Domingo,\" it was later destroyed. In 1644, the Dutch rebuilt it near the original site and named it \"Fort Antonio.\" Since the Dutch were referred to as \"Red-Haired\" people, the fort became known as \"Fort San Domingo.\"\n" +
                    "In 1724, Taiwan began renovating the fort, adding four surrounding gates. After 1867, the British government leased it as a consulate and built a residence nearby. During the Pacific War, Japan declared war on the UK and the US, briefly seizing the fort, but it was returned to the British after the war. Later, it was managed by Australia and the US. In 1980, it was designated as a Class I historic site and opened to the public. Fort San Domingo is considered one of the oldest surviving buildings in Taiwan.", // hongmaocheng
            "Fisherman's Wharf, located on the right bank of the Tamsui River estuary, is famous for its sunset views. In addition to watching the sunset, visitors can walk across the iconic Lover's Bridge, take a ferry along the Blue Highway, or stroll along the wooden boardwalk to enjoy the harbor scenery. They can also ascend the Lover's Tower for a panoramic view of Tamsui.\n" +
                    "Since 1998, the Council of Agriculture has designated this area as a multifunctional demonstration fishing port, providing substantial funding to develop it into a high-quality park combining fishery development and tourism. Fisherman's Wharf has become an important venue for leisure activities, often serving as a filming location for advertisements.", // lovers_bridge
            "Located on the riverbank behind Tamsui Post Office, this bronze statue was created by Mr. Chen Kai-huang from Taipei National University of the Arts. The design depicts the image of Mackay landing on shore, also known as the \"Mackay Art Bronze Statue.\" Dr. Mackay is shown kneeling on the ground with a Bible in his hands, praying, and beside him is the small boat he arrived in. Hence, the full name of this site is \"Mackay Landing Small Ark and Mackay Prayer Statue.\" On the afternoon of March 9, 1872, Canadian missionary Dr. Mackay landed here via the \"Sea Dragon\" ship, making Tamsui his base for missionary work, medical care, and education. He chose Tamsui as his home, where he married, raised a family, and was eventually buried.", // mackay_landing
            "George Leslie Mackay (March 21, 1844 – June 2, 1901) was born in Ontario, Canada. He was a pastor of the Canadian Presbyterian Mission and, although not a doctor, studied anatomy and physiology. He dedicated his life to Taiwan as a missionary. His Chinese name was Jie Ruili, but he was commonly known as \"Dr. Mackay.\" He came to Taiwan in the late 19th century to preach and practice medicine, earning a reputation alongside James Laidlaw Maxwell. Mackay's life was praised with the phrase, \"Rather burn out than rust out.\"", // mackay_statue
            "The Oxford College, also known as the Mackay School, was the first Western-style school in Taiwan. Established in 1882 by Dr. Mackay, who raised funds in his hometown of Oxford County, Canada, it is located within Aletheia University. In 1985, it was designated as a Class II historic site by the Ministry of the Interior. Today, it serves as the university's history museum, showcasing historical and cultural materials related to the school and Mackay.", // oxford_college
            "\"Dancing for Everyone\" is the founding motto of Cloud Gate. In addition to formal theater performances, the dance troupe holds large-scale outdoor performances in different cities in Taiwan every year. There are tens of thousands of spectators at each show. The performance is very orderly and not a single piece of paper is left on the scene. Western media praised it as \"the largest dance performance on the planet.\'", // yunmen_cloud
    };

    // 日文簡介
    public static String[] contsJa = {
            "1882年に建てられた独特の尖塔型の礼拝堂は、地下3階、地上4階、屋上3階の合計10階で構成されています。キリスト教の教えを強調するため、「謙虚な祈りの手」を象徴する尖塔型を主な建物、窓、ドアなどに繰り返し使用し、独特の建築スタイルを創造しました。\n" +
                    "斜面に建てられたこの壮大な礼拝堂は、現在結婚写真の撮影スポットとして人気です。", // chapel
            "淡水川岸には、「ゴールデン・ウォーターフロント」という美しい名前の自転車道があります。この道は、関渡宮近くの中港川入口から淡水老街まで約10キロメートルにわたって続いています。平坦で走りやすいこの道は、家族で白鷺やシオマネキを観察しながら楽しむのに最適です。有名な淡水老街に到着すると、伝統的な米屋、パン屋、布屋、雑貨店などと、現代的なファッションショップや玩具店が混在する独特の雰囲気を感じることができます。老街は川側の部分（ゴールデン・ウォーターフロント遊歩道）と内側の老街に分かれており、地元の特産品として、伝統的な焼き菓子、阿給（アゲ）、イカ焼き、阿婆鉄蛋（アポ・ティエダン）、魚酥（ユースー）、巨大アイスクリーム、魚団子などがあります。これらは老街で必ず食べるべきグルメです。", // gold_seashore
            "滬尾偕医館は、新北市の古跡の一つで、台湾初の西洋式病院です。1879年9月14日にマッケイ博士によって設立され、彼の住居としても使用されました。", // hobe_mackay_hospital
            "紅毛城（閩南語：Âng-mn̂g-siânn）は、新北市淡水区にある古跡です。最初に城が建てられたのは1628年で、北部台湾を支配していたスペイン人によって「サント・ドミンゴ城」として建設されましたが、後に破壊されました。1644年、オランダ人がサント・ドミンゴ城の元の場所近くに再建し、「アントニオ城」と名付けました。当時、オランダ人は「紅毛（赤毛）」と呼ばれていたため、この城は「紅毛城」と呼ばれるようになりました。\n" +
                    "1724年、台湾は城を修復し、周囲に4つの門を追加しました。1867年以降、イギリス政府がこの城を領事館として借り受け、近くに領事館邸を建てました。太平洋戦争中、日本がイギリスとアメリカに宣戦布告し、一時的に城を差し押さえましたが、戦後イギリスに返還されました。その後、オーストラリアとアメリカが順番に管理しました。1980年、この城は一級古跡に指定され、一般公開されました。紅毛城は台湾に現存する最古の建築物の一つとされています。", // hongmaocheng
            "漁人碼頭は淡水川の河口右岸に位置し、夕日で有名です。夕日を眺めるだけでなく、象徴的なラバーズ・ブリッジ（Lover's Bridge）を渡ったり、ブルー・ハイウェイ（Blue Highway）に沿ってフェリーに乗ったり、木製の遊歩道を散策して港の景色を楽しむことができます。また、ラバーズ・タワー（Lover's Tower）に登って淡水の全景を一望することもできます。\n" +
                    "1998年から、行政院農業委員会はこの場所を多機能デモンストレーション漁港に指定し、漁業発展と観光を組み合わせた高品質な公園として開発するために多額の資金を提供しました。漁人碼頭は重要な観光・レジャー活動の場となり、広告の撮影地としても頻繁に使用されています。", // lovers_bridge
            "淡水郵局の後ろの川岸にあるこの銅像は、国立台北藝術大学の陳愷璜氏によって制作されました。銅像はマッケイが上陸するイメージをデザインしており、「マッケイ芸術銅像」とも呼ばれています。マッケイ博士は聖書を手に地面に跪いて祈り、その傍らには彼が乗ってきた小船があります。そのため、この場所の正式名称は「マッケイ上陸の小箱舟とマッケイ祈りの像」です。1872年3月9日の午後3時、カナダの宣教師マッケイ博士は「海龍号」でここに上陸し、淡水を宣教、医療、教育の拠点としました。彼は淡水を家とし、ここで結婚し、家族を持ち、亡くなった後もこの地に埋葬されました。", // mackay_landing
            "ジョージ・レスリー・マッケイ（George Leslie Mackay、1844年3月21日 - 1901年6月2日）は、カナダのオンタリオ州で生まれました。カナダ長老教会の宣教師であり、医師ではありませんでしたが、解剖学と生理学を学びました。彼は生涯を台湾に捧げた宣教師でした。中国語名は「偕睿理」ですが、一般的には「マッケイ博士」と呼ばれています。19世紀末に台湾に来て、宣教と医療活動を行い、ジェームズ・レイドロー・マクスウェル（James Laidlaw Maxwell）と並ぶ名声を得ました。マッケイの人生は「錆びるより燃え尽きることを選ぶ」という言葉で称賛されています。", // mackay_statue
            "理学堂大書院は、台湾で最初の西洋式学校です。1882年にマッケイ博士が故郷のカナダ・オックスフォード郡で資金を調達して設立し、真理大学内に位置しています。「オックスフォード学堂」とも呼ばれ、1985年に内政部によって二級古跡に指定されました。現在は真理大学の歴史博物館として、学校とマッケイ博士に関連する歴史資料を展示しています。", // oxford_college
            "「みんなのために踊る」はクラウド・ゲートの創設モットーです。正式な演劇公演に加え、このダンス団体は毎年台湾のさまざまな都市で大規模な野外公演を行っています。それぞれのショーには数万人の観客が集まります。パフォーマンスは非常に整然としており、現場には一枚の紙も残されていません。欧米メディアはこれを「地球上で最大のダンスパフォーマンス」と称賛した。", // yunmen_cloud
    };

    // 韓文簡介
    public static String[] contsKo = {
            "1882년에 지어진 독특한 첨탑 형태의 예배당은 지하 3층, 지상 4층, 옥탑 3층으로 총 10층으로 구성되어 있습니다. 기독교 교리를 강조하기 위해, '겸손한 기도의 손'을 상징하는 첨탑 형태를 주 건물, 창문, 문 등에 반복적으로 사용하여 독특한 건축 스타일을 창조했습니다.\n" +
                    "경사진 곳에 지어진 이 웅장한 예배당은 현재 결혼 사진 촬영의 명소가 되었습니다.", // chapel
            "탐수이 강변에는 '골든 워터프론트(Golden Waterfront)'라는 아름다운 이름의 자전거 도로가 있습니다. 이 도로는 관두 사원(Guandu Temple) 근처의 중강 강 입구에서 탐수이 올드 스트리트까지 약 10킬로미터에 걸쳐 있습니다. 평평하고 쉽게 탈 수 있는 이 도로는 가족들이 백로와 꽃발게를 관찰하며 즐기기에 적합합니다. 유명한 탐수이 올드 스트리트에 도착하면, 전통 쌀 가게, 빵집, 천 가게, 잡화점 등과 현대적인 패션 매장, 장난감 가게가 어우러진 독특한 분위기를 느낄 수 있습니다. 올드 스트리트는 강변 부분(골든 워터프론트 산책로)과 내부 올드 스트리트로 나뉘며, 현지 특산품으로는 전통 빵, 아게이(아게), 오징어 구이, 할머니의 철계란, 생선 크리스프, 대형 아이스크림, 생선볼 등이 있습니다. 이곳은 반드시 맛봐야 할 음식들로 가득합니다.", // gold_seashore
            "후베이 매케이 병원(滬尾偕醫館)은 신베이시(新北市)의 고적 중 하나로, 대만 최초의 서양식 병원입니다. 1879년 9월 14일 매케이 박사가 설립했으며, 그의 거주지로도 사용되었습니다.", // hobe_mackay_hospital
            "홍모성(紅毛城, Hokkien: Âng-mn̂g-siânn)은 신베이시 탐수이 구에 위치한 고적입니다. 원래 1628년 북부 대만을 지배하던 스페인 사람들이 '산토 도밍고 성(Fort Santo Domingo)'으로 건축했으나, 이후 파괴되었습니다. 1644년 네덜란드 사람들이 원래 위치 근처에 재건축하여 '안토니오 성(Fort Antonio)'이라고 명명했습니다. 당시 네덜란드 사람들을 '홍모(紅毛, 붉은 머리)'라고 불렀기 때문에, 이 성은 '홍모성'으로 불리게 되었습니다.\n" +
                    "1724년 대만은 성을 수리하며 주변에 4개의 문을 추가했습니다. 1867년 이후 영국 정부가 이 성을 영사관으로 임대했으며, 근처에 영사관 저택을 지었습니다. 태평양 전쟁 중 일본이 영국과 미국에 선전포고를 하며 잠시 성을 압수했지만, 전쟁 후 영국에 반환되었습니다. 이후 호주와 미국이 순차적으로 관리를 맡았습니다. 1980년 이 성은 1급 고적으로 지정되어 대중에게 공개되었습니다. 홍모성은 대만에서 현존하는 가장 오래된 건축물 중 하나로 여겨집니다.", // hongmaocheng
            "어부의 부두(Fisherman's Wharf)는 탐수이 강 하구 오른쪽 강변에 위치해 있으며, 석양으로 유명합니다. 석양을 감상하는 것 외에도, 상징적인 러버스 브리지(Lover's Bridge)를 건너거나, 블루 하이웨이(Blue Highway)를 따라 페리를 타고, 나무 산책로를 따라 항구 풍경을 즐길 수 있습니다. 또한 러버스 타워(Lover's Tower)에 올라 탐수이의 전경을 조망할 수도 있습니다.\n" +
                    "1998년부터 농업위원회는 이곳을 다기능 시범 어항으로 지정하고, 어업 발전과 관광을 결합한 고품질 공원으로 개발하기 위해 상당한 자금을 지원했습니다. 어부의 부두는 중요한 관광 및 레저 활동 장소가 되었으며, 광고 촬영지로도 자주 사용됩니다.", // lovers_bridge
            "탐수이 우체국 뒤 강변에 위치한 이 동상은 타이페이 국립 예술대학의 천 카이황(陳愷璜) 선생이 제작한 작품으로, 매케이가 상륙하는 모습을 형상화한 동상이며, '매케이 예술 동상'이라고도 불립니다. 매케이 박사는 성경을 들고 땅에 무릎을 꿇고 기도하는 모습으로, 그의 곁에는 그가 타고 온 작은 배가 있습니다. 따라서 이 장소의 정식 명칭은 '매케이 상륙 작은 방주 및 매케이 기도 동상'입니다. 1872년 3월 9일 오후 3시, 캐나다 선교사 매케이 박사는 '해룡호'를 타고 이곳에 상륙했으며, 이후 탐수이를 선교, 의료, 교육의 중심지로 삼았습니다. 그는 탐수이를 집으로 삼아 여기서 결혼하고 가정을 이루었으며, 사후에도 이 땅에 묻혔습니다.", // mackay_landing
            "조지 레슬리 매케이(George Leslie Mackay, 1844년 3월 21일 ~ 1901년 6월 2일)는 캐나다 온타리오 주에서 태어났습니다. 그는 캐나다 장로교 선교회의 목사였으며, 의사는 아니었지만 해부학과 생리학을 공부했습니다. 그는 생을 대만에 바친 선교사였습니다. 그의 중국어 이름은 제이 루이리(偕睿理)였지만, 일반적으로 '매케이 박사'로 불렸습니다. 그는 19세기 말 대만에 와서 선교와 의료 활동을 펼쳤으며, 제임스 레이들로 맥스웰(James Laidlaw Maxwell)과 함께 명성을 얻었습니다. 매케이의 삶은 '녹슬어 없어지기보다는 타서 없어지겠다'라는 말로 칭송받았습니다.", // mackay_statue
            "옥스퍼드 학당(理學堂大書院)은 대만 최초의 서양식 학교로, 1882년 매케이 박사가 고향인 캐나다 옥스퍼드 카운티에서 기금을 모아 설립했습니다. 이 학교는 현재 알레테이아 대학(真理大學) 내에 위치해 있으며, '옥스퍼드 학당'이라고도 불립니다. 1985년 내정부(內政部)에 의해 2급 고적으로 지정되었으며, 현재는 대학의 역사 박물관으로 사용되고 있습니다. 학교와 매케이 박사와 관련된 역사적 자료를 전시하고 있습니다.", // oxford_college
            "\"모두를 위한 춤\"은 클라우드 게이트의 창립 모토입니다. 정식 연극 공연 외에도, 이 무용단은 매년 대만의 다양한 도시에서 대규모 야외 공연을 개최합니다. 매 공연마다 수만 명의 관객이 모인다. 공연은 매우 질서정연하게 진행되었고, 무대에는 종이 한 장도 남지 않았습니다. 서양 언론은 이를 \"세계에서 가장 큰 댄스 공연\"이라고 칭찬했다.", // yunmen_cloud
    };

    // 網址詳情
    public static String[] url = {
            "https://newtaipei.travel/zh-tw/attractions/detail/402706", // chapel
            "https://newtaipei.travel/zh-tw/attractions/detail/209657", // gold_seashore
            "https://newtaipei.travel/zh-tw/attractions/detail/110411", // hobe_mackay_hospital
            "https://newtaipei.travel/zh-tw/attractions/detail/109672", // hongmaocheng
            "https://www.travel.taipei/zh-tw/attraction/details/120", // lovers_bridge
            "http://tamsui.dils.tku.edu.tw/wiki/index.php/%E9%A6%AC%E5%81%95%E8%97%9D%E8%A1%93%E9%8A%85%E5%83%8F", // mackay_landing
            "https://zh.wikipedia.org/zh-tw/%E9%A6%AC%E5%81%95#%E9%A6%AC%E5%81%95%E4%BE%86%E8%87%BA%E5%88%9D%E6%9C%9F%EF%BC%881871%E5%B9%B412%E6%9C%8830%E6%97%A5-1880%E5%B9%B412%E6%9C%8831%E6%97%A5%EF%BC%89", // mackay_statue
            "https://zh.wikipedia.org/zh-tw/%E7%89%9B%E6%B4%A5%E5%AD%B8%E5%A0%82", // oxford_college
            "https://www.cloudgate.org.tw/cg/about/cloud-gate", // yunmen_cloud
    };

    // 城市
    public static String[] city = {
            "新北市", // chapel
            "新北市", // gold_seashore
            "新北市", // hobe_mackay_hospital
            "新北市", // hongmaocheng
            "新北市", // lovers_bridge
            "新北市", // mackay_landing
            "新北市", // mackay_statue
            "新北市", // oxford_college
            "新北市", // yunmen_cloud
    };

    // 詳細地址
    public static String[] address = {
            "新北市淡水區真理街32號 ", // chapel
            "新北市淡水區環河道路 ", // gold_seashore
            "新北市淡水區馬偕街6號 ", // hobe_mackay_hospital
            "新北市淡水區中正路28巷1號 ", // hongmaocheng
            "新北市淡水區觀海路199號 ", // lovers_bridge
            "新北市淡水區中正路15巷 ", // mackay_landing
            "新北市淡水區中正路-馬偕銅像三角公園", // mackay_statue
            "新北市淡水區真理街32號 ", // oxford_college
            "新北市淡水區中正路一段6巷32-1號 ", // yunmen_cloud
    };

    // 經緯度
    public static double[][] dian = {
            {25.179062, 121.444596}, // chapel
            {25.171581, 121.454296}, // gold_seashore
            {25.17556, 121.449111}, // hobe_mackay_hospital
            {25.179, 121.444021}, // hongmaocheng
            {25.186505, 121.421321}, // lovers_bridge
            {25.17525, 121.447486}, // mackay_landing
            {25.175145, 121.449296}, // mackay_statue
            {25.179786, 121.445046}, // oxford_college
            {25.184501, 121.438852}, // yunmen_cloud
    };
}