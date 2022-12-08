//
//  TrashDayObservableObject.swift
//  iosApp
//
//  Created by Andy O'Brien on 12/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Combine
import shared
public class TrashDayObservableObject : ObservableObject {

    var viewModel : TrashDayViewModel

   /**
    *
    * state flow acts as a state for swift ui here
    *
    */
    @Published private(set) var state: TrashDayState
        
        
    /**
     **
     *fusing the .asObserveable extension funstion we get the wrapped viewmodel and the stateflow
     */
    init(wrapped: TrashDayViewModel) {

        viewModel = wrapped
        state = wrapped.state.value as! TrashDayState
        
        (wrapped.state.asPublisher() as AnyPublisher<TrashDayState, Never>)
            .receive(on: RunLoop.main)
            .assign(to: &$state)
        
    }

}

public extension TrashDayViewModel {

    func asObserveableObject() -> TrashDayObservableObject{
        return TrashDayObservableObject(wrapped: self)
    }

}

