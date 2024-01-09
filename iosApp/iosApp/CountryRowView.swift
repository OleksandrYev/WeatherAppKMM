import SwiftUI
import Shared
import KMPNativeCoroutinesCore
import KMPNativeCoroutinesAsync

struct CountryRowView: View {
    
    @StateObject var viewModel: CountryRowViewModel = CountryRowViewModel()
    
    let isTop: Bool
    
    let action: (String, WeatherEntity) -> ()
    
    @State var country: CountryEntity
    
    var body: some View {
        VStack {
            CountryDetailsView(country: country)
            
            ForEach(country.capital, id: \.self) { capital in
                Button("\(capital) weather") {
                    viewModel.loadWeather(capitalName: capital,
                                          lat: Double(truncating: country.capitalInfo!.latlng[0]),
                                          long: Double(truncating: country.capitalInfo!.latlng[1]),
                                          action: self.action)
                }
            }
        }
    }
    

    @MainActor
    class CountryRowViewModel: ObservableObject {

        let repository: RemoteRepository = RemoteRepository()

        func loadWeather(capitalName: String, lat: Double, long: Double, action: @escaping (String, WeatherEntity) -> ()) {
            Task {
                let result = await asyncResult(for: repository.getWeather(location: LocationEntity(lat: lat, long: long)))
                if case let .success(weather) = result {
                    action(capitalName, weather)
                }
//                let weather = try await asyncFunction(for: repository.getWeather(location: LocationEntity(lat: lat, long: long)))
//                action(capitalName, weather)
            }
        }
    }
    
}
