import SwiftUI
import Shared

struct ContentView: View {
    
    @StateObject var viewModel: ViewModel = ViewModel()
    
    var body: some View {
            if (viewModel.showDetailsView) {
                WeatherView(capitalName: viewModel.capitalName!,
                            details: viewModel.weatherDetails!) {
                    withAnimation {
                        viewModel.showDetailsView.toggle()
                    }
                }.transition(.scale)
            } else {
                CountriesView { 
                    capitalName,
                    weatherDetails in withAnimation {
                        viewModel.showDetailsView.toggle()
                        viewModel.capitalName = capitalName
                        viewModel.weatherDetails = weatherDetails
                    }
                }
            }
        }
    
    class ViewModel: ObservableObject {
        @Published var showDetailsView = false
        @Published var capitalName: String?
        @Published var weatherDetails: WeatherEntity?
    }
}
