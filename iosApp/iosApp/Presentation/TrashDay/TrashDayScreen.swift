//
//  TrashDayScreen.swift
//  iosApp
//
//  Created by Andy O'Brien on 12/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TrashDayScreen: View {
    @ObservedObject var viewModel = ViewModels().getTrashDayViewModel().asObserveableObject()

    var body: some View {
        NavigationView {
            VStack {
                if viewModel.state.isLoading {
                    ProgressView().progressViewStyle(.circular)
                } else {
                    if let it = viewModel.state.streetAddress {
                        Text(it)
                            .font(.headline)
                            .padding(.bottom, 36)
                    }
                    if let it = viewModel.state.nextTrashDay {
                        Text("Your next trash day")
                        Text(it)
                    }
                }
            }
        }
    }
}

struct TrashDayScreen_Previews: PreviewProvider {
    static var previews: some View {
        TrashDayScreen()
    }
}
