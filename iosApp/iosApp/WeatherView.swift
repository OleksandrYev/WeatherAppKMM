import SwiftUI
import Shared
import Kingfisher

struct WeatherView: View {

    let capitalName: String
    let details: WeatherEntity
    let action: () -> ()

    init(capitalName: String, details: WeatherEntity, action: @escaping () -> ()) {
        self.capitalName = capitalName
        self.details = details
        self.action = action
    }

    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 25, style: .continuous)
                .fill(.white)
                .shadow(radius: 10) 
            VStack {
                Button("Back") {
                    action()
                }.frame(width: 300, alignment: .leading).padding(20)
                Text(capitalName).font(.title)
                KFImage(URL(string: details.weather[0].iconUrl)!)
                Text("Feels like: \(details.main.feelsLike)'C / \(CelsiusToFahrenheitKt.celsiusToFahrenheit(celsius: details.main.feelsLike))'F" )
                    .frame(width: 300, alignment: .leading)
                    .padding(5)
                Text("Temp: \(details.main.temperature)'C / \(CelsiusToFahrenheitKt.celsiusToFahrenheit(celsius: details.main.temperature))'F")
                    .frame(width: 300, alignment: .leading)
                    .padding(EdgeInsets(top: 5, leading: 5, bottom: 15, trailing: 5))
            }
        }.frame(width: 300, height: 300)
    }
}
