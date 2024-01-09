import SwiftUI
import Shared
import KMPNativeCoroutinesCore
import KMPNativeCoroutinesAsync

struct CountriesView: View {
    
    enum UiState {
        case loading
        case result([CountryEntity])
        case error(String)
    }
    
    @ObservedObject private(set) var viewModel: CountriesViewModel = CountriesViewModel()
    
    let action: (String, WeatherEntity) -> ()
    
    init(action: @escaping (String, WeatherEntity) -> ()) {
        self.action = action
    }
    
    var body: some View {
        switch viewModel.state {
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
        case .result(let countries):
            return AnyView(
                List(countries.indices) { index in
                    CountryRowView(isTop: index == 0, action: self.action, country: countries[index])
                        .listRowSeparator(.hidden)
                }
            )
        case .error(let description):
            return AnyView(Text(description).multilineTextAlignment(.center))
        }
    }
    
    @MainActor
    class CountriesViewModel: ObservableObject {

        @Published var state = UiState.loading

        let repository: RemoteRepository = RemoteRepository()

        init() {
            self.loadCountries()
        }

        func loadCountries() {
            Task {
                self.state = .loading
                let result = await asyncResult(for: repository.getCountries())
                switch result {
                case .success(let countries) :
                    self.state = .result(countries)
                case .failure(let error) :
                    self.state = .error(error.localizedDescription)
                }
            }
        }
    }
}
